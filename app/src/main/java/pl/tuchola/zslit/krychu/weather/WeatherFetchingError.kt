package pl.tuchola.zslit.krychu.weather

enum class WeatherFetchingError {
    NO_INTERNET_CONNECTION,
    SERVER_INVALID_RESPONSE,
    CONNECTION_TIMEOUT,
    UNRECOGNIZED_ERROR
}