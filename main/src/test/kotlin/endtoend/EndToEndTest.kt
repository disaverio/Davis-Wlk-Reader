package endtoend

import dev.disaverio.wlkconverter.main
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord
import org.junit.Test
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/*
 * Current test is time-consuming and is intended to be run occasionally, as final step just before a new release.
 * It takes the csv generated by this software, and compare it to an original one generated by the original WeatherLink software.
 * Both files (.wlk and .txt) are generated by WeatherLink application, starting from real data collected by a Davis Vantage VUE station.
 */
class EndToEndTest {

    @Test
    fun `The provided output should be correct, comparing it with a real file generated by original WeatherLink software`() {

        val resources = listOf("2020-12")

        resources.forEach { name ->
            printCsvFromOriginalFile("$name.txt")
            printCsvFromWlkFile("$name.wlk")
            assertOutputAreEquals("$name.txt.csv", "DailyData_$name.csv")
            deleteFiles("$name.txt.csv", "DailyData_$name.csv")
        }
    }

    private fun printCsvFromOriginalFile(filename: String) {
        val originalFile = File(javaClass.classLoader.getResource(filename)?.file ?: "")
        val originalFileLines = originalFile.readLines()

        val manLines = originalFileLines
            .filter { it.matches("\\s?\\d{1,2}-\\d{2}-\\d{2}.*".toRegex()) } // only get lines starting with a date
            .map { it.trim().split("\\s+".toRegex()) }
            .map {
                it
                    .filterIndexed { index, _ -> listOf(0, 1, 2, 3, 4, 5, 7, 8, 10, 11, 15, 16, 17, 20, 21, 26, 29).contains(index) } // indexes of "date,time,outsideTemp,hiOutsideTemp,lowOutsideTemp,outsideHum,windSpeed,windDirection,hiWindSpeed,hiWindDirection,barometer,rain,hiRainRate,insideTemp,insideHum,numWindSamples,archiveInterval"
                    .mapIndexed { index, s -> if ((index == 0 && s.length == 7) || (index == 1 && s.length == 4)) "0$s" else s } // add char '0' in front of dates of 7 chars, like 3-12-2020, or times of 4 chars, like 3:29
            }

        val printer = CSVPrinter(FileWriter("${originalFile.absolutePath}.csv"), CSVFormat.DEFAULT)
        printer.printRecords(manLines)
        printer.flush()
        printer.close()
    }

    private fun printCsvFromWlkFile(filename: String) {
        val wlkFile = File(javaClass.classLoader.getResource(filename)?.file ?: "")
        val params = listOf(
            "-i", wlkFile.absolutePath,
            "-p", "${wlkFile.parentFile.absolutePath}/fields",
            "-o", wlkFile.parentFile.absolutePath,
            "--skip-header"
        )
        main(params.toTypedArray())
    }

    private fun assertOutputAreEquals(originalFilename: String, generatedFilename: String) {

        val originalFile = File(javaClass.classLoader.getResource(originalFilename)?.file ?: "")
        val originalRecords = CSVFormat.DEFAULT.parse(FileReader(originalFile.absolutePath)).map {
            Record.fromOriginalFile(it)
        }

        val generatedFile = File(javaClass.classLoader.getResource(generatedFilename)?.file ?: "")
        val generatedRecords = CSVFormat.DEFAULT.parse(FileReader(generatedFile.absolutePath)).map {
            Record.fromGeneratedFile(it)
        }

        generatedRecords.forEach { generatedRecord ->
            val matchedRecords = originalRecords.filter { originalRecord ->
                generatedRecord.date == originalRecord.date && generatedRecord.time == originalRecord.time
            }

            assertTrue { matchedRecords.size < 2 }
            if (matchedRecords.isEmpty()) return@forEach

            val matchedRecord = matchedRecords.single()

            if (matchedRecord.outsideTemp == null || generatedRecord.outsideTemp == null) {
                assertEquals(matchedRecord.outsideTemp, generatedRecord.outsideTemp)
            } else {
                assertEquals(matchedRecord.outsideTemp, generatedRecord.outsideTemp, 0.051)
            }
            if (matchedRecord.hiOutsideTemp == null || generatedRecord.hiOutsideTemp == null) {
                assertEquals(matchedRecord.hiOutsideTemp, generatedRecord.hiOutsideTemp)
            } else {
                assertEquals(matchedRecord.hiOutsideTemp, generatedRecord.hiOutsideTemp, 0.051)
            }
            if (matchedRecord.lowOutsideTemp == null || generatedRecord.lowOutsideTemp == null) {
                assertEquals(matchedRecord.lowOutsideTemp, generatedRecord.lowOutsideTemp)
            } else {
                assertEquals(matchedRecord.lowOutsideTemp, generatedRecord.lowOutsideTemp, 0.051)
            }
            if (matchedRecord.outsideHum == null || generatedRecord.outsideHum == null) {
                assertEquals(matchedRecord.outsideHum, generatedRecord.outsideHum)
            } else {
                assertEquals(matchedRecord.outsideHum, generatedRecord.outsideHum, 0.051)
            }
            if (matchedRecord.windSpeed == null || generatedRecord.windSpeed == null) {
                assertEquals(matchedRecord.windSpeed, generatedRecord.windSpeed)
            } else {
                assertEquals(matchedRecord.windSpeed, generatedRecord.windSpeed, 0.051)
            }
            if (matchedRecord.hiWindSpeed == null || generatedRecord.hiWindSpeed == null) {
                assertEquals(matchedRecord.hiWindSpeed, generatedRecord.hiWindSpeed)
            } else {
                assertEquals(matchedRecord.hiWindSpeed, generatedRecord.hiWindSpeed, 0.051)
            }
            if (matchedRecord.barometer == null || generatedRecord.barometer == null) {
                assertEquals(matchedRecord.barometer, generatedRecord.barometer)
            } else {
                assertEquals(matchedRecord.barometer, generatedRecord.barometer, 0.051)
            }
            if (matchedRecord.rain == null || generatedRecord.rain == null) {
                assertEquals(matchedRecord.rain, generatedRecord.rain)
            } else {
                assertEquals(matchedRecord.rain, generatedRecord.rain, 0.051)
            }
            if (matchedRecord.hiRainRate == null || generatedRecord.hiRainRate == null) {
                assertEquals(matchedRecord.hiRainRate, generatedRecord.hiRainRate)
            } else {
                assertEquals(matchedRecord.hiRainRate, generatedRecord.hiRainRate, 0.051)
            }
            if (matchedRecord.insideTemp == null || generatedRecord.insideTemp == null) {
                assertEquals(matchedRecord.insideTemp, generatedRecord.insideTemp)
            } else {
                assertEquals(matchedRecord.insideTemp, generatedRecord.insideTemp, 0.051)
            }
            if (matchedRecord.insideHum == null || generatedRecord.insideHum == null) {
                assertEquals(matchedRecord.insideHum, generatedRecord.insideHum)
            } else {
                assertEquals(matchedRecord.insideHum, generatedRecord.insideHum, 0.051)
            }
            assertEquals(matchedRecord.windDirection, generatedRecord.windDirection)
            assertEquals(matchedRecord.hiWindDirection, generatedRecord.hiWindDirection)
            assertEquals(matchedRecord.numWindSamples, generatedRecord.numWindSamples)
            assertEquals(matchedRecord.archiveInterval, generatedRecord.archiveInterval)
        }
    }

    private fun deleteFiles(originalFilename: String, generatedFilename: String) {
        val originalFile = File(javaClass.classLoader.getResource(originalFilename).file)
        val generatedFile = File(javaClass.classLoader.getResource(generatedFilename).file)

        Files.deleteIfExists(originalFile.toPath())
        Files.deleteIfExists(generatedFile.toPath())
    }
}

private data class Record(
    val date: LocalDate,
    val time: LocalTime,
    val outsideTemp: Double?,
    val hiOutsideTemp: Double?,
    val lowOutsideTemp: Double?,
    val outsideHum: Double?,
    val windSpeed: Double?,
    val windDirection: String?,
    val hiWindSpeed: Double?,
    val hiWindDirection: String?,
    val barometer: Double?,
    val rain: Double?,
    val hiRainRate: Double?,
    val insideTemp: Double?,
    val insideHum: Double?,
    val numWindSamples: Int?,
    val archiveInterval: Int?
) {
    companion object {
        fun fromCsvRecord(date: LocalDate, record: CSVRecord) =
            Record(
                date = date,
                time = LocalTime.parse(record[1]),
                outsideTemp = getNullableValue(record[2])?.toDouble(),
                hiOutsideTemp = getNullableValue(record[3])?.toDouble(),
                lowOutsideTemp = getNullableValue(record[4])?.toDouble(),
                outsideHum = getNullableValue(record[5])?.toDouble(),
                windSpeed = getNullableValue(record[6])?.toDouble(),
                windDirection = getNullableValue(record[7]),
                hiWindSpeed = getNullableValue(record[8])?.toDouble(),
                hiWindDirection = getNullableValue(record[9]),
                barometer = getNullableValue(record[10])?.toDouble(),
                rain = getNullableValue(record[11])?.toDouble(),
                hiRainRate = getNullableValue(record[12])?.toDouble(),
                insideTemp = getNullableValue(record[13])?.toDouble(),
                insideHum = getNullableValue(record[14])?.toDouble(),
                numWindSamples = getNullableValue(record[15])?.toInt(),
                archiveInterval = getNullableValue(record[16])?.toInt()
            )

        fun fromOriginalFile(record: CSVRecord) =
            fromCsvRecord(
                LocalDate.parse(record[0], DateTimeFormatter.ofPattern("dd-MM-yy")),
                record
            )

        fun fromGeneratedFile(record: CSVRecord) =
            fromCsvRecord(
                LocalDate.parse(record[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                record
            )

        private fun getNullableValue(value: String): String? {
            if (value.isNullOrBlank()) return null
            if (value == "---") return null
            if (value == "null") return null
            return value
        }
    }
}
