/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

import com.util.Constants

def call(){
    def stages_list = Constants.STAGES_GRADLE
    def sStages = params.STAGE

    util.validateStages(sStages, stages_list)

    def str = sStages.split(Constants.SPLIT_SYMBOL);

    for(String values : stages_list){

        switch(values){
            case Constants.STAGE_BUILDANDTEST:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_BUILDANDTEST){
                        env.STG_NAME = Constants.STAGE_BUILDANDTEST
                        bat 'gradle clean build'
                    }
                }
                break
            case Constants.STAGE_SONAR:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_SONAR){
                        env.STG_NAME = Constants.STAGE_SONAR
                        def scannerHome = tool 'sonar-scanner';
                        withSonarQubeEnv('sonar') {
                            bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build -Dsonar.login=75a0e9b0613f563c0e69a23174cf79eb5d4d74c7"
                        }
                    }
                }
                break 
            case Constants.STAGE_RUNJAR:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_RUNJAR){
                        env.STG_NAME = Constants.STAGE_RUNJAR
                        bat 'start gradle bootRun'
                    }
                }
                break
            case Constants.STAGE_REST:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_REST){
                        env.STG_NAME = Constants.STAGE_REST
                        sleep 20
                        bat 'curl http://localhost:8082/rest/mscovid/estadoMundial'
                    }
                }
                break
            case Constants.STAGE_NEXUSCI:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_NEXUSCI){
                        env.STG_NAME = Constants.STAGE_NEXUSCI
                        nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build\\libs\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
                    }
                }
                break
        }
    }

}

return this;