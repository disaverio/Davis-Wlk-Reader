package dev.disaverio.wlkconverter.models.data

import java.time.LocalDate

data class DayData(
    val date: LocalDate,
    val summary: DailySummary,
    val records: Map<Int, WeatherDataRecord>
)