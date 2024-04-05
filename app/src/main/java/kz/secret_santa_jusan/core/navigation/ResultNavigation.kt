package kz.secret_santa_jusan.core.navigation


object ResultNavigation {

    private var result: Any? = null

    fun getValue(): Any?{
        if(result != null) {
            val returnResult = result
            result = null
            return returnResult
        }
        else{
            return null
        }
    }

    fun setValue(result: Any){
        this.result = result
    }
}