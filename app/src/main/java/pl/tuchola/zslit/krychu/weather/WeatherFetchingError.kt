package pl.tuchola.zslit.krychu.weather

enum class WeatherFetchingError {
    NO_INTERNET_CONNECTION,
    CONNECTION_TIMEOUT,
    SERVER_INVALID_RESPONSE,
    UNRECOGNIZED_ERROR
}