package dev.disaverio.wlkreader.service.output

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord

interface Printer {
    fun printDailySummaries(elements: List<DailySummary>, outputPathname: String)
    fun printDailyData(elements: List<WeatherDataRecord>, outputPathname: String)
}