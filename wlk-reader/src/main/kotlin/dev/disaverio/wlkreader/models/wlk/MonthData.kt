package dev.disaverio.wlkreader.models.wlk

import dev.disaverio.wlkreader.models.wlk.structs.HeaderBlock
import java.time.YearMonth

data class MonthData(
    val date: YearMonth,
    val headerBlock: HeaderBlock,
    val dailyData: List<DayData>
)
