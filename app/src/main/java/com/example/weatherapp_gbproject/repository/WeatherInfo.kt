package com.example.weatherapp_gbproject.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherInfo(
    var city: City = getDefaultCity(),
    val temp: Int = 0,
    val feels_like: Int = 0,
    val condition: String = "Cloudy",
    val wind_speed: Double = 2.0,
    val wind_dir: String = "northwest",
    val pressure_mm: Int = 759
) : Parcelable

@Parcelize
data class City(
    val locality: String,
    val lat: Double,
    val lon: Double
) : Parcelable

fun getDefaultCity() = City("Saint-Petersburg", 59.938951, 30.315635)
fun getWorldCities(): List<WeatherInfo> {
    return listOf(
        WeatherInfo(
            City("London", 51.5085300, -0.1257400),
            1,
            2,
            "Rain",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Tokyo", 35.6895000, 139.6917100),
            3,
            4,
            "Overcast",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Paris", 48.8534100, 2.3488000),
            5,
            6,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Berlin", 52.52000659999999, 13.404953999999975),
            7,
            8,
            "Showers",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Roma", 41.9027835, 12.496365500000024),
            9,
            10,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Minsk", 53.90453979999999, 27.561524400000053),
            11,
            12,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Istanbul", 41.0082376, 28.97835889999999),
            13,
            14,
            "Clear",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Washington", 38.9071923, -77.03687070000001),
            15,
            16,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Kyiv", 50.4501, 30.523400000000038),
            17,
            18,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Beijing", 39.90419989999999, 116.40739630000007),
            19,
            20,
            "Cloudy",
            3.0,
            "north",
            761
        )
    )
}

fun getRussianCities(): List<WeatherInfo> {
    return listOf(
        WeatherInfo(
            City("Moscow", 55.755826, 37.617299900000035),
            1,
            2,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Novosibirsk", 55.00835259999999, 82.93573270000002),
            5,
            6,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Yekaterinburg", 56.83892609999999, 60.60570250000001),
            7,
            8,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Nizhniy Novgorod", 56.2965039, 43.936059),
            9,
            10,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Kazan", 55.8304307, 49.06608060000008),
            11,
            12,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Chelyabinsk", 55.1644419, 61.4368432),
            13,
            14,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Omsk", 54.9884804, 73.32423610000001),
            15,
            16,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Rostov-on-Don", 47.2357137, 39.701505),
            17,
            18,
            "Cloudy",
            3.0,
            "north",
            761
        ),
        WeatherInfo(
            City("Ufa", 54.7387621, 55.972055400000045),
            19,
            20,
            "Cloudy",
            3.0,
            "north",
            761
        )
    )

}