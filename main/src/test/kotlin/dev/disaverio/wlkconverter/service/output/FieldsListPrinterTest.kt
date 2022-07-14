package dev.disaverio.wlkconverter.service.output

import dev.disaverio.wlkconverter.utils.readFileLines
import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.types.*
import dev.disaverio.wlkreader.types.units.UnitSystem
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDate
import java.time.LocalTime
import java.util.stream.Stream
import kotlin.reflect.full.declaredMemberProperties
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class FieldsListPrinterTest {

    private val dailySummaryPropsLine = "DailY_SummarY_FIELDS :lowBarTime ,hiSpeed,  unknownProp1, avgOutTemp   , avgChill ,unknownProp2,hiHeatTime,  unknownProp3 "
    private val weatherDataRecordPropsLine = " DAILY_DATA_fields  : windSpeed,unknownProp1 , outsideTemp, lowOutsideTemp,  unknownProp2 , hiRainRate,  unknownProp3 ,  insideTemp "

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
        hiBar = Pressure.fromInchesOfMercury(0.0),
        hiBarTime = LocalTime.of(0, 0),
        avgBar = Pressure.fromInchesOfMercury(0.0),
        lowBar = Pressure.fromInchesOfMercury(0.0),
        lowBarTime = LocalTime.of(13, 47),
        hiSpeed = Speed.fromMilePerHour(10.0),
        dirHiSpeed = WindDirection[0.0],
        hiSpeedTime = LocalTime.of(0, 0),
        avgSpeed = Speed.fromMilePerHour(0.0),
        dailyWindRunTotal = Length.fromMile(0.0),
        hi10MinSpeed = Speed.fromMilePerHour(0.0),
        hi10MinSpeedTime = LocalTime.of(0, 0),
        hi10MinDir = WindDirection[0.0],
        minutesAsDominantDirection = mapOf(
            WindDirection.N to 0,
            WindDirection.S to 1
        ),
        dailyRainTotal = Precipitation.fromInch(0.0),
        hiRainRate = RainRate.fromInchPerHour(0.0),
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
        barometer = Pressure.fromInchesOfMercury(0.0),
        outsideHum = 0.0,
        insideHum = 0.0,
        rain = Precipitation.fromInch(0.0),
        hiRainRate = RainRate.fromInchPerHour(5.0),
        windSpeed = Speed.fromMilePerHour(10.0),
        hiWindSpeed = Speed.fromMilePerHour(0.0),
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

    private val filePath = "fake-file-path"


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetHeader {

        @BeforeAll
        fun mockSetup() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryPropsLine,
                weatherDataRecordPropsLine
            ).shuffled()
        }

        @Test
        fun `should return - in the same order - a list of only known property names defined in the 1st line of file pointed by path passed to constructor, when list's elements type is 'DailySummary'`() {
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertEquals(listOf("lowBarTime", "hiSpeed", "avgOutTemp", "avgChill", "hiHeatTime"), p.getHeader(listOf(dailySummary)))
        }

        @Test
        fun `should return - in the same order - a list of only known property names defined in the 2nd line of file pointed by path passed to constructor, when list's elements type is 'WeatherDataRecord'`() {
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertEquals(listOf("windSpeed", "outsideTemp", "lowOutsideTemp", "hiRainRate", "insideTemp"), p.getHeader(listOf(weatherDataRecord)))
        }

        @Test
        fun `should return empty list as header, when elements list is empty`() {
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.getHeader(listOf<DailySummary>()).isEmpty() }
            assertTrue { p.getHeader(listOf<WeatherDataRecord>()).isEmpty() }
        }

        @Test
        fun `should provide full fields list as header, when error occurs trying to open fields list file`() {
            every { readFileLines(filePath) } throws Exception("whatever")
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertEquals(DailySummary::class.declaredMemberProperties.toList().map { it.name }, p.getHeader(listOf(dailySummary)))
            assertEquals(WeatherDataRecord::class.declaredMemberProperties.toList().map { it.name }, p.getHeader(listOf(weatherDataRecord)))
        }

        @Test
        fun `should provide full fields list as header, when null is passed as fields list file path`() {
            val p = FieldsListPrinterExtension(filePath = null)

            assertEquals(DailySummary::class.declaredMemberProperties.toList().map { it.name }, p.getHeader(listOf(dailySummary)))
            assertEquals(WeatherDataRecord::class.declaredMemberProperties.toList().map { it.name }, p.getHeader(listOf(weatherDataRecord)))
        }

        @Test
        fun `should throw Exception, when elements list type is unknown`() {
            val p = FieldsListPrinterExtension(filePath = filePath)
            val ex = assertThrows<Exception> { p.getHeader(listOf(0)) }

            assertEquals("Unknown element type.", ex.message)
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetRequestedFields {

        @BeforeAll
        fun mockSetup() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryPropsLine,
                weatherDataRecordPropsLine
            ).shuffled()
        }

        @ParameterizedTest
        @MethodSource("getGetRequestedDailySummaryFieldsTestArguments")
        fun `should return values of properties defined in the 1st line of file pointed by path passed to constructor, when list's elements type is 'DailySummary' - the value depends on unit system passed to printer`(
            unitSystem: UnitSystem?,
            expectedValues: String
        ) {
            val p = FieldsListPrinterExtension(unitSystem, filePath)

            assertEquals(listOf(expectedValues.split(",")), p.getRequestedFields(listOf(dailySummary)))
        }

        @ParameterizedTest
        @MethodSource("getGetRequestedWeatherDataRecordFieldsTestArguments")
        fun `should return values of properties defined in the 2nd line of file pointed by path passed to constructor, when list's elements type is 'WeatherDataRecord' - the value depends on unit system passed to printer`(
            unitSystem: UnitSystem?,
            expectedValues: String
        ) {
            val p = FieldsListPrinterExtension(unitSystem, filePath)

            assertEquals(listOf(expectedValues.split(",")), p.getRequestedFields(listOf(weatherDataRecord)))
        }

        @Test
        fun `should provide full list of values, when error occurs trying to open fields list file`() {
            every { readFileLines(filePath) } throws Exception("whatever")
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertEquals(listOf(listOf("0.00", "31.50", "-17.78", "-17.78", "-17.78", "0.0", "13.50", "0.00", "-17.78", "0.0", "0.00", "0.0", "0.0", "0.00", "0", "0001-01-01", "N", "N", "0.00", "00:00", "0.00", "00:00", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "18:21", "0.0", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "00:00", "0.00", "00:00", "0", "16.09", "00:00", "-17.78", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "0.00", "0.00", "0.00", "13:47", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "0", "{N=0, S=1}", "0")), p.getRequestedFields(listOf(dailySummary)))
            assertEquals(listOf(listOf("0.0", "0.0", "0", "0.00", "0001-01-01", "0", "-17.78", "127.00", "0.0", "N", "0.00", "0", "0.0", "10.00", "32.50", "0", "0.0", "38.00", "0", "0.00", "0", "00:00", "N", "16.09")), p.getRequestedFields(listOf(weatherDataRecord)))
        }

        @Test
        fun `should provide full list of values, when null is passed as fields list file path`() {
            val p = FieldsListPrinterExtension(filePath = null)

            assertEquals(listOf(listOf("0.00", "31.50", "-17.78", "-17.78", "-17.78", "0.0", "13.50", "0.00", "-17.78", "0.0", "0.00", "0.0", "0.0", "0.00", "0", "0001-01-01", "N", "N", "0.00", "00:00", "0.00", "00:00", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "18:21", "0.0", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "00:00", "0.00", "00:00", "0", "16.09", "00:00", "-17.78", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "0.00", "0.00", "0.00", "13:47", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "00:00", "0.0", "00:00", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "00:00", "-17.78", "0", "{N=0, S=1}", "0")), p.getRequestedFields(listOf(dailySummary)))
            assertEquals(listOf(listOf("0.0", "0.0", "0", "0.00", "0001-01-01", "0", "-17.78", "127.00", "0.0", "N", "0.00", "0", "0.0", "10.00", "32.50", "0", "0.0", "38.00", "0", "0.00", "0", "00:00", "N", "16.09")), p.getRequestedFields(listOf(weatherDataRecord)))
        }

        @Test
        fun `should throw Exception, when elements list type is unknown`() {
            val p = FieldsListPrinterExtension(filePath = filePath)
            val ex = assertThrows<Exception> { p.getRequestedFields(listOf(0)) }

            assertEquals("Unknown element type.", ex.message)
        }

        private fun getGetRequestedDailySummaryFieldsTestArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(null, "13:47,16.09,13.50,31.50,18:21"),
                Arguments.of(UnitSystem.SI, "13:47,4.47,13.50,31.50,18:21"),
                Arguments.of(UnitSystem.IMPERIAL, "13:47,10.00,56.30,88.70,18:21")
            )

        private fun getGetRequestedWeatherDataRecordFieldsTestArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(null, "16.09,38.00,32.50,127.00,10.00"),
                Arguments.of(UnitSystem.SI, "4.47,38.00,32.50,127.00,10.00"),
                Arguments.of(UnitSystem.IMPERIAL, "10.00,100.40,90.50,5.00,50.00")
            )
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PrintDailySummaries {

        @BeforeAll
        fun mockSetup() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryPropsLine,
                weatherDataRecordPropsLine
            ).shuffled()
        }

        @Test
        fun `should be true when a list of daily summaries props is provided`() {
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.printDailySummaries }
        }

        @Test
        fun `should be true when error occurs trying to open fields list file`() {
            every { readFileLines(filePath) } throws Exception("whatever")
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.printDailySummaries }
        }

        @Test
        fun `should be true when null is passed as fields list file path`() {
            val p = FieldsListPrinterExtension(filePath = null)

            assertTrue { p.printDailySummaries }
        }

        @Test
        fun `should be true when daily summary fields line, in fields list file, is not provided`() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                weatherDataRecordPropsLine
            )
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.printDailySummaries }
        }

        @Test
        fun `should be false when daily summary fields line, in fields list file, is empty`() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                "daily_summary_fields:",
                weatherDataRecordPropsLine
            )
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertFalse { p.printDailySummaries }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PrintDailyData {

        @BeforeAll
        fun mockSetup() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryPropsLine,
                weatherDataRecordPropsLine
            ).shuffled()
        }

        @Test
        fun `should be true when a list of daily data props is provided`() {
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.printDailyData }
        }

        @Test
        fun `should be true when error occurs trying to open fields list file`() {
            every { readFileLines(filePath) } throws Exception("whatever")
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.printDailyData }
        }

        @Test
        fun `should be true when null is passed as fields list file path`() {
            val p = FieldsListPrinterExtension(filePath = null)

            assertTrue { p.printDailyData }
        }

        @Test
        fun `should be true when daily data fields line, in fields list file, is not provided`() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryPropsLine
            ).shuffled()
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertTrue { p.printDailyData }
        }

        @Test
        fun `should be false when daily data fields line, in fields list file, is empty`() {
            mockkStatic("dev.disaverio.wlkconverter.utils.FileHelperKt")
            every { readFileLines(filePath) } returns listOf(
                dailySummaryPropsLine,
                "daily_data_fields:"
            ).shuffled()
            val p = FieldsListPrinterExtension(filePath = filePath)

            assertFalse { p.printDailyData }
        }
    }
}


private class FieldsListPrinterExtension(unitSystem: UnitSystem? = null, filePath: String?) : FieldsListPrinter(unitSystem, filePath) {
    public override fun <T> getHeader(elements: List<T>) = super.getHeader(elements)
    public override fun <T> getRequestedFields(elements: List<T>) = super.getRequestedFields(elements)
    public override val printDailySummaries = super.printDailySummaries
    public override val printDailyData = super.printDailyData
}
