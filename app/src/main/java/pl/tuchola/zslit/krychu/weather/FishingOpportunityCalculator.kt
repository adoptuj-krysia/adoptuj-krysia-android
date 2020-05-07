package pl.tuchola.zslit.krychu.weather

class FishingOpportunityCalculator(private val weather: Weather) {

    private val rainWords = listOf("śnieg", "deszcz", "opad")

    fun getFishingOpportunity() : FishingOpportunity {
        rainWords.forEach { rainWord ->
            if(rainWord in weather.weatherDescription) {
                return FishingOpportunity.BAD_RAIN
            }
        }

        if(weather.temperatureCelcius < 8) {
            return FishingOpportunity.BAD_TEMPERATURE
        }

        return FishingOpportunity.GOOD
    }

}