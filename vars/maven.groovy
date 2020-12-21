/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
    def stages_list = ['build', 'test', 'jar', 'sonar', 'run', 'testing', 'nexus']

    println "Stages a ejecutar {$params.STAGE}"
    def sStages = params.STAGE
    def str = sStages.split(';');

    for(String values : str){
        if (!stages_list.contains(values.trim())){
            env.STG_NAME = "Stage no valido: ${values}"
            error "Stage no valido"
        }
    }

    for(String values : stages_list){

        switch(values){
            case 'build':
                stage('build'){
                    env.STG_NAME = 'build'
                    bat 'mvnw.cmd clean compile -e'
                }
                break
            case 'test':
                stage('test'){
                    env.STG_NAME = 'test'
                    bat 'mvnw.cmd clean test -e'
                }
                break
            case 'jar':
                stage('jar') {
                    env.STG_NAME = 'Jar'
                    bat 'mvnw.cmd clean package -e'
                }
                break
            case 'sonar':
                stage('sonar'){
                    env.STG_NAME = 'sonar'
                    def scannerHome = tool 'sonar-scanner';
                    withSonarQubeEnv('sonar') {
                        bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build -Dsonar.login=75a0e9b0613f563c0e69a23174cf79eb5d4d74c7"
                    }
                }
                break 
            case 'run':
                stage('run'){
                    env.STG_NAME = 'run'
                    bat 'start mvnw.cmd spring-boot:run'
                }
                break
            case 'testing':
                stage('testing'){
                    env.STG_NAME = 'testing'
                    sleep 10
                    bat 'curl http://localhost:8082/rest/mscovid/estadoMundial'
                }
                break
            case 'nexus':
                stage('nexus'){
                    env.STG_NAME = 'nexus'
                    nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
                }
                break
        }
        
        
        
        
        
        
        
    }

}

return this;