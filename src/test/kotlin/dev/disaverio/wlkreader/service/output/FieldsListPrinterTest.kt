package dev.disaverio.wlkreader.service.output

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.types.*
import dev.disaverio.wlkreader.utils.readFileLines
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class FieldsListPrinterTest {

    private val dailySummaryProps = listOf("lowBarTime", "unknownProp1", "avgOutTemp", "avgChill", "unknownProp2", "hiHeatTime", "unknownProp3").joinToString(",")
    private val weatherDataRecordProps = listOf("unknownProp1", "outsideTemp", "lowOutsideTemp", "unknownProp2", "hiRainRate", "unknownProp3", "insideTemp").joinToString(",")

    private val dailySummary = DailySummary(
        date = LocalDate.of(1, 1, 1),
        dataSpan = 0,
        hiInTemp = Temperature.fromFahrenheit(0.0),
        hiInTempTime = LocalTime.of(0, 0),
        avgInTemp = Temperature.fromFahrenheit(0.0),
        lowInTemp = Temperature.fromFahrenheit(0.0),
        lowInTempTime = LocalTime.of(0, 0),
        hiInHum = 0.0,
        hiInHumTime = LocalTime.of(0, 0),
        lowInHum = 0.0,
        lowInHumTime = LocalTime.of(0, 0),
        hiOutTemp = Temperature.fromFahrenheit(0.0),
        hiOutTempTime = LocalTime.of(0, 0),
        avgOutTemp = Temperature.fromFahrenheit(56.3),
        lowOutTemp = Temperature.fromFahrenheit(0.0),
        lowOutTempTime = LocalTime.of(0, 0),
        hiChill = Temperature.fromFahrenheit(0.0),
        hiChillTime = LocalTime.of(0, 0),
        avgChill = Temperature.fromFahrenheit(88.7),
        lowChill = Temperature.fromFahrenheit(0.0),
        lowChillTime = LocalTime.of(0, 0),
        hiDew = Temperature.fromFahrenheit(0.0),
        hiDewTime = LocalTime.of(0, 0),
        avgDew = Temperature.fromFahrenheit(0.0),
        lowDew = Temperature.fromFahrenheit(0.0),
        lowDewTime = LocalTime.of(0, 0),
        hiOutHum = 0.0,
        hiOutHumTime = LocalTime.of(0, 0),
        avgOutHum = 0.0,
        lowOutHum = 0.0,
        lowOutHumTime = LocalTime.of(0, 0),
        hiBar = Pressure.fromInHg(0.0),
        hiBarTime = LocalTime.of(0, 0),
        avgBar = Pressure.fromInHg(0.0),
        lowBar = Pressure.fromInHg(0.0),
        lowBarTime = LocalTime.of(13, 47),
        hiSpeed = Speed.fromMilesph(0.0),
        dirHiSpeed = WindDirection[0.0],
        hiSpeedTime = LocalTime.of(0, 0),
        avgSpeed = Speed.fromMilesph(0.0),
        dailyWindRunTotal = Distance.fromMiles(0.0),
        hi10MinSpeed = Speed.fromMilesph(0.0),
        hi10MinSpeedTime = LocalTime.of(0, 0),
        hi10MinDir = WindDirection[0.0],
        minutesAsDominantDirection = mapOf(
            WindDirection.N to 0,
            WindDirection.S to 1
        ),
        dailyRainTotal = Precipitation.fromInches(0.0),
        hiRainRate = RainRate.fromInchesPerHour(0.0),
        hiRainRateTime = LocalTime.of(0, 0),
        dailyUVDose = 0.0,
        hiUV = 0.0,
        hiUVTime = LocalTime.of(0, 0),
        integratedHeatDD65 = DeltaTemperature.fromFahrenheit(0.0),
        integratedCoolDD65 = DeltaTemperature.fromFahrenheit(0.0),
        hiHeat = Temperature.fromFahrenheit(0.0),
        hiHeatTime = LocalTime.of(18, 21),
        avgHeat = Temperature.fromFahrenheit(0.0),
        lowHeat = Temperature.fromFahrenheit(0.0),
        lowHeatTime = LocalTime.of(0, 0),
        hiTHSW = Temperature.fromFahrenheit(0.0),
        hiTHSWTime = LocalTime.of(0, 0),
        lowTHSW = Temperature.fromFahrenheit(0.0),
        lowTHSWTime = LocalTime.of(0, 0),
        hiTHW = Temperature.fromFahrenheit(0.0),
        hiTHWTime = LocalTime.of(0, 0),
        lowTHW = Temperature.fromFahrenheit(0.0),
        lowTHWTime = LocalTime.of(0, 0),
        numWindPackets = 0,
        hiSolar = 0,
        dailySolarEnergy = 0.0,
        minSunlight = 0,
        dailyETTotal = 0.0,
        hiWetBulb = Temperature.fromFahrenheit(0.0),
        lowWetBulb = Temperature.fromFahrenheit(0.0),
        avgWetBulb = Temperature.fromFahrenheit(0.0)
    )

    private val weatherDataRecord = WeatherDataRecord(
        date = LocalDate.of(1, 1, 1),
        time = LocalTime.of(0, 0),
        archiveInterval = 0,
        packedTime = 0,
        outsideTemp = Temperature.fromFahrenheit(100.4),
        hiOutsideTemp = Temperature.fromFahrenheit(0.0),
        lowOutsideTemp = Temperature.fromFahrenheit(90.5),
        insideTemp = Temperature.fromFahrenheit(50.0),
        barometer = Pressure.fromInHg(0.0),
        outsideHum = 0.0,
        insideHum = 0.0,
        rain = Precipitation.fromInches(0.0),
        hiRainRate = RainRate.fromInchesPerHour(5.0),
        windSpeed = Speed.fromMilesph(0.0),
        hiWindSpeed = Speed.fromMilesph(0.0),
        windDirection = WindDirection[0.0],
        hiWindDirection = WindDirection[0.0],
        numWindSamples = 0,
        solarRad = 0,
        hisolarRad = 0,
        UV = 0.0,
        hiUV = 0.0,
        extraRad = 0,
        ET = 0.0,
    )

    @Nested
    inner class GetHeader {

        @Test
        fun `should return - in the same order - a list of only known property names defined in the 1st line of file pointed by path passed to constructor, when list's elements type is 'DailySummary'`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getHeader(elements: List<T>) = super.getHeader(elements)
            }

            assertEquals(listOf("lowBarTime", "avgOutTemp", "avgChill", "hiHeatTime"), p.getHeader(listOf(dailySummary)))
        }

        @Test
        fun `should return - in the same order - a list of only known property names defined in the 2nd line of file pointed by path passed to constructor, when list's elements type is 'WeatherDataRecord'`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getHeader(elements: List<T>) = super.getHeader(elements)
            }

            assertEquals(listOf("outsideTemp", "lowOutsideTemp", "hiRainRate", "insideTemp"), p.getHeader(listOf(weatherDataRecord)))
        }

        @Test
        fun `should return empty list as header, when elements list is empty`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getHeader(elements: List<T>) = super.getHeader(elements)
            }

            assertTrue { p.getHeader(listOf<DailySummary>()).isEmpty() }
            assertTrue { p.getHeader(listOf<WeatherDataRecord>()).isEmpty() }
        }

        @Test
        fun `should throw Exception, when elements list type is unknown`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getHeader(elements: List<T>) = super.getHeader(elements)
            }

            val ex = assertThrows<Exception> { p.getHeader(listOf(0)) }
            assertEquals("Unknown element type.", ex.message)
        }
    }

    @Nested
    inner class GetRequestedFields {

        @Test
        fun `should return - in the same order - a list of values of properties defined in the 1st line of file pointed by path passed to constructor, when list's elements type is 'DailySummary'`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getRequestedFields(elements: List<T>) = super.getRequestedFields(elements)
            }

            assertEquals(listOf(listOf("13:47", "13.50", "31.50", "18:21")), p.getRequestedFields(listOf(dailySummary)))
        }

        @Test
        fun `should return values of properties defined in the 2nd line of file pointed by path passed to constructor, when list's elements type is 'WeatherDataRecord'`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getRequestedFields(elements: List<T>) = super.getRequestedFields(elements)
            }

            assertEquals(listOf(listOf("38.00", "32.50", "127.00", "10.00")), p.getRequestedFields(listOf(weatherDataRecord)))
        }

        @Test
        fun `should throw Exception, when elements list type is unknown`() {

            val filePath = "fake-file-path"

            mockkStatic("dev.disaverio.wlkreader.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryProps,
                weatherDataRecordProps
            )

            val p = object : FieldsListPrinter(filePath) {
                public override fun <T> getRequestedFields(elements: List<T>) = super.getRequestedFields(elements)
            }

            val ex = assertThrows<Exception> { p.getRequestedFields(listOf(0)) }
            assertEquals("Unknown element type.", ex.message)
        }
    }
}