package dev.disaverio.wlkconverter.service.output.csv

import dev.disaverio.wlkconverter.service.output.FieldsListPrinter
import dev.disaverio.wlkconverter.service.output.Printer
import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.types.units.UnitSystem
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.FileWriter

class CsvPrinter(unitSystem: UnitSystem? = null, outputFieldsListFilename: String?): FieldsListPrinter(unitSystem, outputFieldsListFilename), Printer {

    override fun printDailySummaries(elements: List<DailySummary>, outputPathname: String) {
        if (printDailySummaries) print(elements, outputPathname)
    }

    override fun printDailyData(elements: List<WeatherDataRecord>, outputPathname: String) {
        if (printDailyData) print(elements, outputPathname)
    }

    private inline fun<reified T> print(elements: List<T>, outputPathname: String) {
        val printer = CSVPrinter(FileWriter(outputPathname), CSVFormat.DEFAULT)
        printer.printRecord(getHeader(elements))
        printer.printRecords(getRequestedFields(elements))
        printer.flush()
        printer.close()
    }
}