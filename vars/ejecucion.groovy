def call(){
    pipeline {
        agent any

        parameters {
            choice(name: 'CHOICE', choices: ['maven', 'gradle'], description: 'Herramienta de construccion')
        }

        stages {
            stage('Pipeline') {
                steps {
                    script{
                        env.STG_NAME = ''

                        if(params.CHOICE == 'manen'){
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