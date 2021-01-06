import com.util.Constants

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
                        //bat 'set'
                        util.baseOS()
                        env.STG_NAME = ''
                        
                        figlet params.CHOICE

                        env.PIPELINE_TYPE = util.pipelineType(env.BRANCH_NAME)

                        if(env.PIPELINE_TYPE == Constants.IC){
                            if(params.CHOICE == Constants.MAVEN){
                                maven.call()
                            } else {
                                gradle.call()
                            }
                        }else{
                            release.call()
                        }
                    }
                }
            }
        }
        post {
            success {
                slackSend color: 'good', message: "Build Success: [Carlo Figueroa][${env.JOB_NAME}][${params.CHOICE}] Ejecucion exitosa.", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token-diplomado'
            }
            failure {
                slackSend color: 'danger', message: "Build Failure: [Carlo Figueroa][${env.JOB_NAME}][${params.CHOICE}] Ejecucion fallida en stage [${env.STG_NAME}].", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-token-diplomado'
            }
        }
    }
}

return this;