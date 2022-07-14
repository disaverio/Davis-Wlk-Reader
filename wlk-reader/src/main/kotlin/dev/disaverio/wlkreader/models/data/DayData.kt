package dev.disaverio.wlkreader.models.data

import java.time.LocalDate

data class DayData(
    val date: LocalDate,
    val summary: DailySummary,
    val records: List<WeatherDataRecord>
)
