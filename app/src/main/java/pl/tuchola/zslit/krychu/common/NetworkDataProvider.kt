package pl.tuchola.zslit.krychu.common

interface NetworkDataProvider<DataType, ErrorType> {

    fun startFetching(onSuccess: (DataType) -> Unit, onError: (ErrorType) -> Unit)

}