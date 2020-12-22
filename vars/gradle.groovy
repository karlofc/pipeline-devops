/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
    def stages_list = util.validStages()
    def sStages = params.STAGE

    util.validateStages(sStages)

    def str = sStages.split(';');

    for(String values : stages_list){

        switch(values){
            case 'build':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('build'){
                        env.STG_NAME = 'build'
                        bat 'gradle clean build'
                    }
                }
                break
            case 'test':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('test'){
                        env.STG_NAME = 'test'
                        bat 'gradle test'
                    }
                }
                break
            case 'jar':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('jar') {
                        env.STG_NAME = 'Jar'
                        bat 'gradle jar'
                    }
                }
                break
            case 'sonar':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('sonar'){
                        env.STG_NAME = 'sonar'
                        def scannerHome = tool 'sonar-scanner';
                        withSonarQubeEnv('sonar') {
                            bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build -Dsonar.login=75a0e9b0613f563c0e69a23174cf79eb5d4d74c7"
                        }
                    }
                }
                break 
            case 'run':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('run'){
                        env.STG_NAME = 'run'
                        bat 'start gradle bootRun'
                    }
                }
                break
            case 'testing':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('testing'){
                        env.STG_NAME = 'testing'
                        sleep 20
                        bat 'curl http://localhost:8082/rest/mscovid/estadoMundial'
                    }
                }
                break
            case 'nexus':
                if(sStages.trim() == '' || str.contains(values)){
                    stage('nexus'){
                        env.STG_NAME = 'nexus'
                        nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build\\libs\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
                    }
                }
                break
        }
    }

}

return this;