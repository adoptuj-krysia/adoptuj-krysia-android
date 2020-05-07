package pl.tuchola.zslit.krychu.utils

interface NetworkDataProvider<DataType, ErrorType> {

    fun provide(onSuccess: (DataType) -> Unit, onError: (ErrorType) -> Unit)

}