package dev.disaverio.wlkconverter.service

import dev.disaverio.wlkconverter.service.output.OutputFormat
import dev.disaverio.wlkconverter.service.output.Printer
import dev.disaverio.wlkconverter.service.output.csv.CsvPrinter
import dev.disaverio.wlkreader.service.WlkReader
import dev.disaverio.wlkreader.types.units.UnitSystem

class Service(
    unitSystem: UnitSystem?,
    outputPathname: String?,
    outputFormat: OutputFormat?,
    outputFieldsListFilename: String?
) {
    private val printer: Printer
    private val format: OutputFormat = outputFormat ?: OutputFormat.CSV
    private val outPathname = outputPathname ?: ".${format.fileExt}"

    init {
        printer = when(format) {
            OutputFormat.CSV -> CsvPrinter(unitSystem, outputFieldsListFilename)
        }
    }

    fun printMonth(inputPathname: String) {
        val yearmonth = inputPathname.split("/").last().split(".").first()
        val dailySummaryPathname = getDailySummaryPathname(yearmonth, outPathname)
        val dailyDataPathname = getDailyDataPathname(yearmonth, outPathname)

        val monthData = WlkReader.readMonth(yearmonth.split("-").first().toInt(), yearmonth.split("-").last().toInt(), inputPathname)
        printer.printDailySummaries(monthData.dailyData.values.map { it.summary }, dailySummaryPathname)
        printer.printDailyData(monthData.dailyData.values.map { it.records.values }.flatten(), dailyDataPathname)
    }

    private fun getDailySummaryPathname(prefix: String, pathname: String) =
        getChangedFilename("DailySummary_$prefix", pathname)

    private fun getDailyDataPathname(prefix: String, pathname: String) =
        getChangedFilename("DailyData_$prefix", pathname)

    private fun getChangedFilename(prefix: String, pathname: String): String {
        val tokens = pathname.split("/").toMutableList()
        val filename = tokens.removeLast()
        tokens.add("$prefix$filename")
        return tokens.joinToString("/")
    }
}