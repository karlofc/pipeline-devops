/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
    def stages_list = ['build', 'test', 'jar', 'sonar', 'run', 'testing', 'nexus']

    println "Stages a ejecutar {$params.STAGE}"
    def str = params.STAGE.trim(';')

    for( String values : str )
        println(values)

    stage('build'){
        env.STG_NAME = 'build'
        bat 'mvnw.cmd clean compile -e'
    }
    stage('test'){
        env.STG_NAME = 'test'
        bat 'mvnw.cmd clean test -e'
    }
    stage('jar') {
        env.STG_NAME = 'Jar'
        bat 'mvnw.cmd clean package -e'
    }
    stage('sonar'){
        env.STG_NAME = 'sonar'
        def scannerHome = tool 'sonar-scanner';
        withSonarQubeEnv('sonar') {
            bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build -Dsonar.login=75a0e9b0613f563c0e69a23174cf79eb5d4d74c7"
        }
    }
    stage('run'){
        env.STG_NAME = 'run'
        bat 'start mvnw.cmd spring-boot:run'
    }
    stage('testing'){
        env.STG_NAME = 'testing'
        sleep 10
        bat 'curl http://localhost:8082/rest/mscovid/estadoMundial'
    }
    stage('nexus'){
        env.STG_NAME = 'nexus'
        nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build\\DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
    }

}

return this;