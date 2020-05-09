package pl.tuchola.zslit.krychu.common

abstract class NetworkDataProvider<DataType, ErrorType> {

    protected var onSuccess: ((DataType) -> Unit)? = null

    protected var onError: ((ErrorType) -> Unit)? = null

    abstract fun startFetching()

    abstract fun cancelFetching()

    fun setOnSuccessListener(listener: (DataType) -> Unit) {
        onSuccess = listener
    }

    fun setOnErrorListener(listener: (ErrorType) -> Unit) {
        onError = listener
    }

}