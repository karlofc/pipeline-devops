def validStages() {
    def stages_list = ['build', 'test', 'jar', 'sonar', 'run', 'testing', 'nexus']
    return stages_list
}

def baseOS(){
    def os = ''

    if(isUnix){
        os = 'Unix'    
    } else {
        os = 'Windows'
    }

    println "Jenkins OS [${os}]"
}

def validateStages(sStages){
    def stages_list = validStages()

    if(sStages.trim() == ''){
        println "Stages a ejecutar [TODOS]"
    }else{
        println "Stages a ejecutar [{$sStages}]"
    }

    def str = sStages.split(';');

    if(sStages.trim() != ''){
        for(String values : str){
            if (!stages_list.contains(values.trim())){
                env.STG_NAME = "Stage no valido: ${values}"
                error "Stage no valido"
            }
        }
    }
}