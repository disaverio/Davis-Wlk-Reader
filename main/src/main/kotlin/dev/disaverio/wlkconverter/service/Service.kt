package dev.disaverio.wlkconverter.service

import dev.disaverio.wlkconverter.service.output.OutputFormat
import dev.disaverio.wlkconverter.service.output.Printer
import dev.disaverio.wlkconverter.service.output.csv.CsvPrinter
import dev.disaverio.wlkreader.service.WlkReader
import dev.disaverio.wlkreader.types.units.UnitSystem

class Service(
    unitSystem: UnitSystem?,
    outputFieldsListFilename: String?,
    private val outputFolderPath: String,
    private val outputFormat: OutputFormat
) {
    private val printer: Printer

    init {
        printer = when(outputFormat) {
            OutputFormat.CSV -> CsvPrinter(unitSystem, outputFieldsListFilename)
        }
    }

    fun printMonth(inputPathname: String) {
        val yearmonth = inputPathname.split("/").last().split(".").first()

        val monthData = WlkReader.readMonthlyFile(inputPathname)
        printer.printDailySummaries(monthData.dailyData.map { it.summary }, "$outputFolderPath/DailySummary_$yearmonth.${outputFormat.fileExt}")
        printer.printDailyData(monthData.dailyData.map { it.records }.flatten(), "$outputFolderPath/DailyData_$yearmonth.${outputFormat.fileExt}")
    }
}
