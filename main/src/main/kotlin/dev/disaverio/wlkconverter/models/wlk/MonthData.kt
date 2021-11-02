package dev.disaverio.wlkconverter.models.wlk

import java.time.YearMonth

data class MonthData(
    val date: YearMonth,
    val headerBlock: HeaderBlock,
    val dailyData: Map<Int, DayData>
)