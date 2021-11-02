package dev.disaverio.wlkreader.models.wlk

import dev.disaverio.wlkreader.models.wlk.structs.DailySummary1
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary2
import dev.disaverio.wlkreader.models.wlk.structs.WeatherDataRecord
import java.time.LocalDate

data class DayData(
    val date: LocalDate,
    val dailySummary1: DailySummary1,
    val dailySummary2: DailySummary2,
    val weatherRecords: Map<Int, WeatherDataRecord>
)