package dev.disaverio.wlkreader.models.data

import dev.disaverio.wlkreader.types.*
import java.time.LocalDate
import java.time.LocalDateTime

/*
 * Model with fields translated from DailySummary1 and DailySummary2 wlk raw data.
 * Not translated fields:
 *   DailySummary1.dataType
 *   DailySummary1.reserved
 *   DailySummary2.reserved
 *   DailySummary2.dirBins
 *   DailySummary2.timeValues
 *   DailySummary2.reserved2
 *   DailySummary2.todaysWeather
 *   DailySummary2.dataType
 */
data class DailySummary(
    val date: LocalDate,
    val dataSpan: Int,
    val hiInTemp: Temperature?,
    val hiInTempTime: LocalDateTime?,
    val avgInTemp: Temperature?,
    val lowInTemp: Temperature?,
    val lowInTempTime: LocalDateTime?,
    val hiInHum: Double?,
    val hiInHumTime: LocalDateTime?,
    val lowInHum: Double?,
    val lowInHumTime: LocalDateTime?,
    val hiOutTemp: Temperature?,
    val hiOutTempTime: LocalDateTime?,
    val avgOutTemp: Temperature?,
    val lowOutTemp: Temperature?,
    val lowOutTempTime: LocalDateTime?,
    val hiChill: Temperature?,
    val hiChillTime: LocalDateTime?,
    val avgChill: Temperature?,
    val lowChill: Temperature?,
    val lowChillTime: LocalDateTime?,
    val hiDew: Temperature?,
    val hiDewTime: LocalDateTime?,
    val avgDew: Temperature?,
    val lowDew: Temperature?,
    val lowDewTime: LocalDateTime?,
    val hiOutHum: Double?,
    val hiOutHumTime: LocalDateTime?,
    val avgOutHum: Double?,
    val lowOutHum: Double?,
    val lowOutHumTime: LocalDateTime?,
    val hiBar: Pressure?,
    val hiBarTime: LocalDateTime?,
    val avgBar: Pressure?,
    val lowBar: Pressure?,
    val lowBarTime: LocalDateTime?,
    val hiSpeed: Speed?,
    val dirHiSpeed: WindDirection?,
    val hiSpeedTime: LocalDateTime?,
    val avgSpeed: Speed?,
    val dailyWindRunTotal: Length?,
    val hi10MinSpeed: Speed?,
    val hi10MinSpeedTime: LocalDateTime?,
    val hi10MinDir: WindDirection?,
    val minutesAsDominantDirection: Map<WindDirection, Int>,
    val dailyRainTotal: Precipitation,
    val hiRainRate: RainRate,
    val hiRainRateTime: LocalDateTime?,
    val dailyUVDose: Double?,
    val hiUV: Double,
    val hiUVTime: LocalDateTime?,
    val integratedHeatDD65: DeltaTemperature?,
    val integratedCoolDD65: DeltaTemperature?,
    val hiHeat: Temperature?,
    val hiHeatTime: LocalDateTime?,
    val avgHeat: Temperature?,
    val lowHeat: Temperature?,
    val lowHeatTime: LocalDateTime?,
    val hiTHSW: Temperature?,
    val hiTHSWTime: LocalDateTime?,
    val lowTHSW: Temperature?,
    val lowTHSWTime: LocalDateTime?,
    val hiTHW: Temperature?,
    val hiTHWTime: LocalDateTime?,
    val lowTHW: Temperature?,
    val lowTHWTime: LocalDateTime?,
    val numWindPackets: Int,
    val hiSolar: Int,
    val dailySolarEnergy: Double,
    val minSunlight: Int,
    val dailyETTotal: Double,
    val hiWetBulb: Temperature?,
    val lowWetBulb: Temperature?,
    val avgWetBulb: Temperature?
)
