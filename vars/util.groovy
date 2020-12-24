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

def validateStages(sStages){
    def stages_list = Constants.STAGES

    if(sStages.trim() == ''){
        println "Stages a ejecutar [TODOS]"
    }else{
        println "Stages a ejecutar [${sStages}]"
    }

    def str = sStages.split(';');

    if(sStages.trim() != ''){
        for(String values : str){
            if (!stages_list.contains(values.trim())){
                env.STG_NAME = "${values} (no valido)"
                error "Stage no valido: ${values}"
            }
        }
    }
}