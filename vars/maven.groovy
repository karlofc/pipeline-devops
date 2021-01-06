import com.util.Constants

/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
    def stages_list = Constants.STAGES_MAVEN
    def sStages = params.STAGE

    util.validateStages(sStages, stages_list)

    def str = sStages.split(Constants.SPLIT_SYMBOL);

    println "${stages_list}"
    
    for(String values : stages_list){

        switch(values){
            case Constants.STAGE_BUILD:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_BUILD){
                        env.STG_NAME = Constants.STAGE_BUILD
                        bat 'mvnw.cmd clean compile -e'
                    }
                }
                break
            case Constants.STAGE_TEST:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_TEST){
                        env.STG_NAME = Constants.STAGE_TEST
                        bat 'mvnw.cmd clean test -e'
                    }
                }
                break
            case Constants.STAGE_JAR:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_JAR) {
                        env.STG_NAME = Constants.STAGE_JAR
                        bat 'mvnw.cmd clean package -e'
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
                        bat 'start mvnw.cmd spring-boot:run'
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
                        nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
                    }
                }
                break
        }
    }

}

return this;