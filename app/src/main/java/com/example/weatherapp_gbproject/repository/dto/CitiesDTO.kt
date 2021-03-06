package com.example.weatherapp_gbproject.repository.dto


data class CitiesDTO(
    val locality: String,
    val lat: Double,
    val lon: Double,
    val isRussian: Boolean
)
fun getListCities(): List<CitiesDTO> {
    return listOf(
    CitiesDTO("London", 51.5085300, -0.1257400,false),
        CitiesDTO("Tokyo", 35.6895000, 139.6917100,false),
        CitiesDTO("Paris", 48.8534100, 2.3488000,false),
        CitiesDTO("Berlin", 52.52000659999999, 13.404953999999975,false),
        CitiesDTO("Roma", 41.9027835, 12.496365500000024,false),
        CitiesDTO("Minsk", 53.90453979999999, 27.561524400000053,false),
        CitiesDTO("Istanbul", 41.0082376, 28.97835889999999,false),
        CitiesDTO("Washington", 38.9071923, -77.03687070000001,false),
        CitiesDTO("Kyiv", 50.4501, 30.523400000000038,false),
        CitiesDTO("Beijing", 39.90419989999999, 116.40739630000007,false),
        CitiesDTO("Moscow", 55.755826, 37.617299900000035,true),
        CitiesDTO("Saint-Petersburg", 59.938951, 30.315635,true),
        CitiesDTO("Novosibirsk", 55.00835259999999, 82.93573270000002,true),
        CitiesDTO("Yekaterinburg", 56.83892609999999, 60.60570250000001,true),
        CitiesDTO("Nizhniy Novgorod", 56.2965039, 43.936059,true),
        CitiesDTO("Chelyabinsk", 55.1644419, 61.4368432,true),
        CitiesDTO("Omsk", 54.9884804, 73.32423610000001,true),
        CitiesDTO("Rostov-on-Don", 47.2357137, 39.701505,true),
        CitiesDTO("Ufa", 54.7387621, 55.972055400000045,true),
)}

