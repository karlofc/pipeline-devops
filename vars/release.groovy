import com.util.Constants

def call(){
    def stages_list = Constants.STAGES
    def sStages = params.STAGE

    util.validateStages(sStages)

    def str = sStages.split(Constants.SPLIT_SYMBOL);

    for(String values : stages_list){

        switch(values){
            case "A":
                break;
}

return this;