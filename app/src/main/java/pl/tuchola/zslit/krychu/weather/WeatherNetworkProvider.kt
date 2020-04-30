package pl.tuchola.zslit.krychu.weather

interface WeatherNetworkProvider {
    fun startFetchingWeather (
        onSuccess: (Weather) -> Unit,
        onFailure: (WeatherFetchingError) -> Unit
    )
}