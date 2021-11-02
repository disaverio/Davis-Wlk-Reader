package dev.disaverio.wlkreader.models.wlk

import java.time.LocalDate

data class DayData(
    val date: LocalDate,
    val dailySummary1: DailySummary1,
    val dailySummary2: DailySummary2,
    val weatherRecords: Map<Int, WeatherDataRecord>
)