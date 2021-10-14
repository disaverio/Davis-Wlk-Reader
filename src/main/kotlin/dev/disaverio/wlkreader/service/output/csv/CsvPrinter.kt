package dev.disaverio.wlkreader.service.output.csv

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.service.output.FieldsListPrinter
import dev.disaverio.wlkreader.service.output.Printer
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.FileWriter

class CsvPrinter(outputFieldsListFilename: String?): FieldsListPrinter(outputFieldsListFilename), Printer {

    override fun printDailySummaries(elements: List<DailySummary>, outputPathname: String) =
        print(elements, outputPathname)

    override fun printDailyData(elements: List<WeatherDataRecord>, outputPathname: String) =
        print(elements, outputPathname)

    private inline fun<reified T> print(elements: List<T>, outputPathname: String) {
        val printer = CSVPrinter(FileWriter(outputPathname), CSVFormat.DEFAULT)
        printer.printRecord(getHeader(elements))
        printer.printRecords(getRequestedFields(elements))
        printer.flush()
        printer.close()
    }
}