package dev.disaverio.wlkreader.models.data

import dev.disaverio.wlkreader.types.*
import java.time.LocalDate
import java.time.LocalTime

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
    val hiInTempTime: LocalTime?,
    val avgInTemp: Temperature?,
    val lowInTemp: Temperature?,
    val lowInTempTime: LocalTime?,
    val hiInHum: Double?,
    val hiInHumTime: LocalTime?,
    val lowInHum: Double?,
    val lowInHumTime: LocalTime?,
    val hiOutTemp: Temperature?,
    val hiOutTempTime: LocalTime?,
    val avgOutTemp: Temperature?,
    val lowOutTemp: Temperature?,
    val lowOutTempTime: LocalTime?,
    val hiChill: Temperature?,
    val hiChillTime: LocalTime?,
    val avgChill: Temperature?,
    val lowChill: Temperature?,
    val lowChillTime: LocalTime?,
    val hiDew: Temperature?,
    val hiDewTime: LocalTime?,
    val avgDew: Temperature?,
    val lowDew: Temperature?,
    val lowDewTime: LocalTime?,
    val hiOutHum: Double?,
    val hiOutHumTime: LocalTime?,
    val avgOutHum: Double?,
    val lowOutHum: Double?,
    val lowOutHumTime: LocalTime?,
    val hiBar: Pressure?,
    val hiBarTime: LocalTime?,
    val avgBar: Pressure?,
    val lowBar: Pressure?,
    val lowBarTime: LocalTime?,
    val hiSpeed: Speed?,
    val dirHiSpeed: WindDirection?,
    val hiSpeedTime: LocalTime?,
    val avgSpeed: Speed?,
    val dailyWindRunTotal: Distance?,
    val hi10MinSpeed: Speed?,
    val hi10MinSpeedTime: LocalTime?,
    val hi10MinDir: WindDirection?,
    val minutesAsDominantDirection: Map<WindDirection, Int>,
    val dailyRainTotal: Precipitation,
    val hiRainRate: RainRate,
    val hiRainRateTime: LocalTime?,
    val dailyUVDose: Double?,
    val hiUV: Double,
    val hiUVTime: LocalTime?,
    val integratedHeatDD65: DeltaTemperature?,
    val integratedCoolDD65: DeltaTemperature?,
    val hiHeat: Temperature?,
    val hiHeatTime: LocalTime?,
    val avgHeat: Temperature?,
    val lowHeat: Temperature?,
    val lowHeatTime: LocalTime?,
    val hiTHSW: Temperature?,
    val hiTHSWTime: LocalTime?,
    val lowTHSW: Temperature?,
    val lowTHSWTime: LocalTime?,
    val hiTHW: Temperature?,
    val hiTHWTime: LocalTime?,
    val lowTHW: Temperature?,
    val lowTHWTime: LocalTime?,
    val numWindPackets: Int,
    val hiSolar: Int,
    val dailySolarEnergy: Double,
    val minSunlight: Int,
    val dailyETTotal: Double,
    val hiWetBulb: Temperature?,
    val lowWetBulb: Temperature?,
    val avgWetBulb: Temperature?
)