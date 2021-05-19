import com.util.Constants

def baseOS(){
    def os = ''

    if(isUnix()){
        os = 'Unix/Linux/MacOS'    
    } else {
        os = 'Windows'
    }

    println "Jenkins OS [${os}]"
}

def validateStages(sStages, stages_list){
    //def stages_list = Constants.STAGES

    if(sStages.trim() == ''){
        println "Stages a ejecutar [TODOS]"
    }else{
        println "Stages a ejecutar [${sStages}]"
    }

    def str = sStages.split(Constants.SPLIT_SYMBOL);

    if(sStages.trim() != ''){
        for(String values : str){
            if (!stages_list.contains(values.trim())){
                env.STG_NAME = "${values} (no valido)"
                error "Stage no valido: ${values}"
            }
        }
    }
}

def pipelineType(branch_name){
    def pipeline_type = ''

    if(branch_name ==~ /develop/ || branch_name ==~ /feature-.*/){
        pipeline_type = Constants.IC
        figlet "continuous integration"
    } else {
        pipeline_type = Constants.RELEASE
        figlet "continuous deployment"
    }

    println "Pipeline Type [${pipeline_type}]"

    return pipeline_type
}