package pl.tuchola.zslit.krychu.news

enum class ZslitConnectionError {
    TIMEOUT,
    INVALID_SERVER_RESPONSE,
    NO_NEWS_AVAILABLE,
    UNRECOGNIZED_ERROR
}