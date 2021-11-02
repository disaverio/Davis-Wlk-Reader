package dev.disaverio.wlkconverter.models.data

import java.time.YearMonth

data class MonthData(
    val date: YearMonth,
    val dailyData: Map<Int, DayData>
)