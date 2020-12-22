def call(){
    pipeline {
        agent any

        parameters {
            choice(name: 'CHOICE', choices: ['maven', 'gradle'], description: 'Herramienta de construccion')
            string(name: 'STAGE', defaultValue: '', description: 'Stages a ejecutar')
        }

        stages {
            stage('Pipeline') {
                steps {
                    script{
                        util.baseOS
                        env.STG_NAME = ''

                        if(params.CHOICE == 'maven'){
                            maven.call()
                        } else {
                            gradle.call()
                        }
                    }
                }
            }
        }
        post {
            success {
                slackSend color: 'good', message: "Build Success: [Carlo Figueroa][${env.JOB_NAME}][${params.CHOICE}] Ejecución exitosa.", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token-diplomado'
            }
            failure {
                slackSend color: 'danger', message: "Build Failure: [Carlo Figueroa][${env.JOB_NAME}][${params.CHOICE}] Ejecución fallida en stage [${env.STG_NAME}].", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token-diplomado'
            }
        }
    }
}

return this;