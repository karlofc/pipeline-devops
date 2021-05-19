import com.util.Constants

def call(){
    def stages_list = Constants.STAGES_RELEASE
    def sStages = params.STAGE

    util.validateStages(sStages, stages_list)

    def str = sStages.split(Constants.SPLIT_SYMBOL);

    for(String values : stages_list){

        switch(values){
            case Constants.STAGE_DOWNLOADNEXUS:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_DOWNLOADNEXUS){
                        env.STG_NAME = Constants.STAGE_DOWNLOADNEXUS
                        bat 'curl -X GET -u admin:P@ssw0rd2201 http://localhost:8081/repository/test-nexus/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar -O'
                    }
                }
                break
            case Constants.STAGE_RUNDOWNLOADEDNEXUS:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_RUNDOWNLOADEDNEXUS){
                        env.STG_NAME = Constants.STAGE_RUNDOWNLOADEDNEXUS
                        bat 'start java -jar DevOpsUsach2020-0.0.1.jar'
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
            case Constants.STAGE_NEXUSCD:
                if(sStages.trim() == '' || str.contains(values)){
                    stage(Constants.STAGE_NEXUSCD){
                        env.STG_NAME = Constants.STAGE_NEXUSCD
                        nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'test-nexus', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.0']]]
                    }
                }
                break
        }
    }
}

return this;