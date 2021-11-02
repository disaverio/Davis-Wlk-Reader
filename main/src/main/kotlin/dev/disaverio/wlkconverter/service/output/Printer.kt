package dev.disaverio.wlkconverter.service.output

import dev.disaverio.wlkconverter.models.data.DailySummary
import dev.disaverio.wlkconverter.models.data.WeatherDataRecord

interface Printer {
    fun printDailySummaries(elements: List<DailySummary>, outputPathname: String)
    fun printDailyData(elements: List<WeatherDataRecord>, outputPathname: String)
}