package dev.disaverio.wlkconverter.models.data

import dev.disaverio.wlkconverter.types.*
import java.time.LocalDate
import java.time.LocalTime

/*
 * Model with fields translated from WeatherDataRecord wlk raw data.
 * Not translated fields:
 *   WeatherDataRecord.dataType
 *   WeatherDataRecord.iconFlags
 *   WeatherDataRecord.moreFlags
 *   WeatherDataRecord.leafTemp
 *   WeatherDataRecord.newSensors
 *   WeatherDataRecord.forecast
 *   WeatherDataRecord.soilTemp
 *   WeatherDataRecord.soilMoisture
 *   WeatherDataRecord.leafWetness
 *   WeatherDataRecord.extraTemp
 *   WeatherDataRecord.extraHum
 */
class WeatherDataRecord(
    val date: LocalDate,
    val time: LocalTime,
    val archiveInterval: Int,
    val packedTime: Int,
    val outsideTemp: Temperature?,
    val hiOutsideTemp: Temperature?,
    val lowOutsideTemp: Temperature?,
    val insideTemp: Temperature?,
    val barometer: Pressure?,
    val outsideHum: Double?,
    val insideHum: Double?,
    val rain: Precipitation,
    val hiRainRate: RainRate,
    val windSpeed: Speed?,
    val hiWindSpeed: Speed?,
    val windDirection: WindDirection?,
    val hiWindDirection: WindDirection?,
    val numWindSamples: Int,
    val solarRad: Int,
    val hisolarRad: Int,
    val UV: Double,
    val hiUV: Double,
    val extraRad: Int,
    val ET: Double
)