package dev.disaverio.wlkreader.service

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.DayData
import dev.disaverio.wlkreader.models.data.MonthData
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary1
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary2
import dev.disaverio.wlkreader.models.wlk.structs.HeaderBlock
import dev.disaverio.wlkreader.types.WindDirection
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import kotlin.test.assertEquals
import dev.disaverio.wlkreader.models.wlk.DayData as WlkDayData
import dev.disaverio.wlkreader.models.wlk.MonthData as WlkMonthData
import dev.disaverio.wlkreader.models.wlk.structs.WeatherDataRecord as WlkWeatherDataRecord

class WlkTransformerTest {

    @Test
    fun `fromWlk should translate to MonthData model from wlk MonthData raw model using the right translator`() {
        val monthData = WlkMonthData(
            date = YearMonth.of(2021, 10),
            headerBlock = getHeaderBlock(),
            dailyData = listOf(getWlkDayData())
        )
        val result = WlkTransformer.fromWlk(monthData)

        assertMonthDataAreEqual(monthData, result)
    }

    @Test
    fun `fromWlk should translate to DayData model from wlk DayData raw model using the right translator`() {
        val dayData = getWlkDayData()
        val result = WlkTransformer.fromWlk(dayData)

        assertDayDataAreEqual(dayData, result)
    }

    @Test
    fun `fromWlk should translate to WeatherDataRecord model from wlk WeatherDataRecord raw model using the right translator`() {
        val record = getWlkWeatherDataRecord()
        val date = LocalDate.of(2021, 10, 22)
        val result = WlkTransformer.fromWlk(date, record)

        assertEquals(WlkFieldsTranslator.getDateTime(date, record.packedTime), LocalDateTime.of(result.date, result.time))
        assertWeatherDataRecordAreEqual(record, result)
    }

    @Test
    fun `fromWlk should translate to DailySummary model from DailySummary1 and DailySummary2 raw models using the right translator`() {
        val ds1 = getWlkDailySummary1()
        val ds2 = getWlkDailySummary2()
        val date = LocalDate.of(2021, 10, 22)
        val result = WlkTransformer.fromWlk(date, ds1, ds2)

        assertEquals(date, result.date)
        assertDailySummaryAreEqual(ds1, ds2, result)
    }

    private fun assertMonthDataAreEqual(wlkMonthData: WlkMonthData, monthData: MonthData) {
        assertEquals(wlkMonthData.date, monthData.date)
        assertEquals(wlkMonthData.dailyData.size, monthData.dailyData.size)
        wlkMonthData.dailyData.forEach { wlkDayData ->
            val dayData = monthData.dailyData.single { wlkDayData.date == it.date }
            assertDayDataAreEqual(wlkDayData, dayData)
        }
    }

    private fun assertDayDataAreEqual(wlkDayData: WlkDayData, dayData: DayData) {
        assertEquals(wlkDayData.date, dayData.date)
        assertDailySummaryAreEqual(wlkDayData.dailySummary1, wlkDayData.dailySummary2, dayData.summary)

        wlkDayData.weatherRecords.forEach { wlkWeatherRecord ->
            val weatherRecord = dayData.records.single { WlkFieldsTranslator.getDateTime(wlkDayData.date, wlkWeatherRecord.packedTime) == LocalDateTime.of(it.date, it.time) }
            assertWeatherDataRecordAreEqual(wlkWeatherRecord, weatherRecord)
        }
    }

    private fun assertWeatherDataRecordAreEqual(wlkDataRecord: WlkWeatherDataRecord, dataRecord: WeatherDataRecord) {
        assertEquals(WlkFieldsTranslator.getArchiveInterval(wlkDataRecord.archiveInterval), dataRecord.archiveInterval)
        assertEquals(WlkFieldsTranslator.getDimensionlessValue(wlkDataRecord.packedTime), dataRecord.packedTime)
        assertEquals(WlkFieldsTranslator.getTemperature(wlkDataRecord.outsideTemp)?.fahrenheit, dataRecord.outsideTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(wlkDataRecord.hiOutsideTemp)?.fahrenheit, dataRecord.hiOutsideTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(wlkDataRecord.lowOutsideTemp)?.fahrenheit, dataRecord.lowOutsideTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(wlkDataRecord.insideTemp)?.fahrenheit, dataRecord.insideTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getPressure(wlkDataRecord.barometer)?.inchesofmercury, dataRecord.barometer?.inchesofmercury)
        assertEquals(WlkFieldsTranslator.getHumidity(wlkDataRecord.outsideHum), dataRecord.outsideHum)
        assertEquals(WlkFieldsTranslator.getHumidity(wlkDataRecord.insideHum), dataRecord.insideHum)
        assertEquals(WlkFieldsTranslator.getPrecipitationFromClicksQuantity(wlkDataRecord.rain).millimetre, dataRecord.rain.millimetre)
        assertEquals(WlkFieldsTranslator.getRainRateFromClicksNumber(wlkDataRecord.rain, wlkDataRecord.hiRainRate).millimetreperhour, dataRecord.hiRainRate.millimetreperhour)
        assertEquals(WlkFieldsTranslator.getSpeed(wlkDataRecord.windSpeed)?.mileperhour, dataRecord.windSpeed?.mileperhour)
        assertEquals(WlkFieldsTranslator.getSpeed(wlkDataRecord.hiWindSpeed)?.mileperhour, dataRecord.hiWindSpeed?.mileperhour)
        assertEquals(WlkFieldsTranslator.getWindDirection(wlkDataRecord.windDirection), dataRecord.windDirection)
        assertEquals(WlkFieldsTranslator.getWindDirection(wlkDataRecord.hiWindDirection), dataRecord.hiWindDirection)
        assertEquals(WlkFieldsTranslator.getDimensionlessValueUnsigned(wlkDataRecord.numWindSamples), dataRecord.numWindSamples)
        assertEquals(WlkFieldsTranslator.getDimensionlessValue(wlkDataRecord.solarRad), dataRecord.solarRad)
        assertEquals(WlkFieldsTranslator.getDimensionlessValue(wlkDataRecord.hisolarRad), dataRecord.hisolarRad)
        assertEquals(WlkFieldsTranslator.getUvIndex(wlkDataRecord.UV), dataRecord.UV)
        assertEquals(WlkFieldsTranslator.getUvIndex(wlkDataRecord.hiUV), dataRecord.hiUV)
        assertEquals(WlkFieldsTranslator.getDimensionlessValue(wlkDataRecord.extraRad), dataRecord.extraRad)
        assertEquals(WlkFieldsTranslator.getEvapotranspiration(wlkDataRecord.ET), dataRecord.ET)
    }

    private fun assertDailySummaryAreEqual(ds1: DailySummary1, ds2: DailySummary2, ds: DailySummary) {
        assertEquals(WlkFieldsTranslator.getDataSpan(ds1.dataSpan), ds.dataSpan)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.hiOutTemp)?.fahrenheit, ds.hiOutTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.lowOutTemp)?.fahrenheit, ds.lowOutTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.hiInTemp)?.fahrenheit, ds.hiInTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.lowInTemp)?.fahrenheit, ds.lowInTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.avgOutTemp)?.fahrenheit, ds.avgOutTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.avgInTemp)?.fahrenheit, ds.avgInTemp?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.hiChill)?.fahrenheit, ds.hiChill?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.lowChill)?.fahrenheit, ds.lowChill?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.hiDew)?.fahrenheit, ds.hiDew?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.lowDew)?.fahrenheit, ds.lowDew?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.avgChill)?.fahrenheit, ds.avgChill?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds1.avgDew)?.fahrenheit, ds.avgDew?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getHumidity(ds1.hiOutHum), ds.hiOutHum)
        assertEquals(WlkFieldsTranslator.getHumidity(ds1.lowOutHum), ds.lowOutHum)
        assertEquals(WlkFieldsTranslator.getHumidity(ds1.hiInHum), ds.hiInHum)
        assertEquals(WlkFieldsTranslator.getHumidity(ds1.lowInHum), ds.lowInHum)
        assertEquals(WlkFieldsTranslator.getHumidity(ds1.avgOutHum), ds.avgOutHum)
        assertEquals(WlkFieldsTranslator.getPressure(ds1.hiBar)?.inchesofmercury, ds.hiBar?.inchesofmercury)
        assertEquals(WlkFieldsTranslator.getPressure(ds1.lowBar)?.inchesofmercury, ds.lowBar?.inchesofmercury)
        assertEquals(WlkFieldsTranslator.getPressure(ds1.avgBar)?.inchesofmercury, ds.avgBar?.inchesofmercury)
        assertEquals(WlkFieldsTranslator.getSpeed(ds1.hiSpeed)?.mileperhour, ds.hiSpeed?.mileperhour)
        assertEquals(WlkFieldsTranslator.getSpeed(ds1.avgSpeed)?.mileperhour, ds.avgSpeed?.mileperhour)
        assertEquals(WlkFieldsTranslator.getWindRun(ds1.dailyWindRunTotal)?.mile, ds.dailyWindRunTotal?.mile)
        assertEquals(WlkFieldsTranslator.getSpeed(ds1.hi10MinSpeed)?.mileperhour, ds.hi10MinSpeed?.mileperhour)
        assertEquals(WlkFieldsTranslator.getWindDirection(ds1.dirHiSpeed), ds.dirHiSpeed)
        assertEquals(WlkFieldsTranslator.getWindDirection(ds1.hi10MinDir), ds.hi10MinDir)
        assertEquals(WlkFieldsTranslator.getPrecipitation(ds1.dailyRainTotal).inch, ds.dailyRainTotal.inch)
        assertEquals(WlkFieldsTranslator.getRainRate(ds1.hiRainRate).inchperhour, ds.hiRainRate.inchperhour)
        assertEquals(WlkFieldsTranslator.getUvMedIndex(ds1.dailyUVDose), ds.dailyUVDose)
        assertEquals(WlkFieldsTranslator.getUvIndex(ds1.hiUV), ds.hiUV)
        assertEquals(WlkFieldsTranslator.getHiOutTempTime(ds.date, ds1.timeValues), ds.hiOutTempTime)
        assertEquals(WlkFieldsTranslator.getLowOutTempTime(ds.date, ds1.timeValues), ds.lowOutTempTime)
        assertEquals(WlkFieldsTranslator.getHiInTempTime(ds.date, ds1.timeValues), ds.hiInTempTime)
        assertEquals(WlkFieldsTranslator.getLowInTempTime(ds.date, ds1.timeValues), ds.lowInTempTime)
        assertEquals(WlkFieldsTranslator.getHiChillTime(ds.date, ds1.timeValues), ds.hiChillTime)
        assertEquals(WlkFieldsTranslator.getLowChillTime(ds.date, ds1.timeValues), ds.lowChillTime)
        assertEquals(WlkFieldsTranslator.getHiDewTime(ds.date, ds1.timeValues), ds.hiDewTime)
        assertEquals(WlkFieldsTranslator.getLowDewTime(ds.date, ds1.timeValues), ds.lowDewTime)
        assertEquals(WlkFieldsTranslator.getHiOutHumTime(ds.date, ds1.timeValues), ds.hiOutHumTime)
        assertEquals(WlkFieldsTranslator.getLowOutHumTime(ds.date, ds1.timeValues), ds.lowOutHumTime)
        assertEquals(WlkFieldsTranslator.getHiInHumTime(ds.date, ds1.timeValues), ds.hiInHumTime)
        assertEquals(WlkFieldsTranslator.getLowInHumTime(ds.date, ds1.timeValues), ds.lowInHumTime)
        assertEquals(WlkFieldsTranslator.getHiBarTime(ds.date, ds1.timeValues), ds.hiBarTime)
        assertEquals(WlkFieldsTranslator.getLowBarTime(ds.date, ds1.timeValues), ds.lowBarTime)
        assertEquals(WlkFieldsTranslator.getHiSpeedTime(ds.date, ds1.timeValues), ds.hiSpeedTime)
        assertEquals(WlkFieldsTranslator.getHi10MinSpeedTime(ds.date, ds1.timeValues), ds.hi10MinSpeedTime)
        assertEquals(WlkFieldsTranslator.getHiRainRateTime(ds.date, ds1.timeValues), ds.hiRainRateTime)
        assertEquals(WlkFieldsTranslator.getHiUVTime(ds.date, ds1.timeValues), ds.hiUVTime)
        assertEquals(WlkFieldsTranslator.getDeltaTemperature(ds2.integratedHeatDD65)?.fahrenheit, ds.integratedHeatDD65?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getDeltaTemperature(ds2.integratedCoolDD65)?.fahrenheit, ds.integratedCoolDD65?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.hiHeat)?.fahrenheit, ds.hiHeat?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.lowHeat)?.fahrenheit, ds.lowHeat?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.avgHeat)?.fahrenheit, ds.avgHeat?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.hiTHSW)?.fahrenheit, ds.hiTHSW?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.lowTHSW)?.fahrenheit, ds.lowTHSW?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.hiTHW)?.fahrenheit, ds.hiTHW?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.lowTHW)?.fahrenheit, ds.lowTHW?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getHiHeatTime(ds.date, ds2.timeValues), ds.hiHeatTime)
        assertEquals(WlkFieldsTranslator.getLowHeatTime(ds.date, ds2.timeValues), ds.lowHeatTime)
        assertEquals(WlkFieldsTranslator.getHiTHSWTime(ds.date, ds2.timeValues), ds.hiTHSWTime)
        assertEquals(WlkFieldsTranslator.getLowTHSWTime(ds.date, ds2.timeValues), ds.lowTHSWTime)
        assertEquals(WlkFieldsTranslator.getHiTHWTime(ds.date, ds2.timeValues), ds.hiTHWTime)
        assertEquals(WlkFieldsTranslator.getLowTHWTime(ds.date, ds2.timeValues), ds.lowTHWTime)
        assertEquals(WindDirection.values().associateWith { WlkFieldsTranslator.getMinutesAsDominantDirection(it, ds2.dirBins) }, ds.minutesAsDominantDirection)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.hiWetBulb)?.fahrenheit, ds.hiWetBulb?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.lowWetBulb)?.fahrenheit, ds.lowWetBulb?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getTemperature(ds2.avgWetBulb)?.fahrenheit, ds.avgWetBulb?.fahrenheit)
        assertEquals(WlkFieldsTranslator.getDimensionlessValueUnsigned(ds2.numWindPackets), ds.numWindPackets)
        assertEquals(WlkFieldsTranslator.getDimensionlessValue(ds2.hiSolar), ds.hiSolar)
        assertEquals(WlkFieldsTranslator.getDimensionlessValueFromTenths(ds2.dailySolarEnergy), ds.dailySolarEnergy)
        assertEquals(WlkFieldsTranslator.getDimensionlessValue(ds2.minSunlight), ds.minSunlight)
        assertEquals(WlkFieldsTranslator.getDimensionlessValueFromThousandths(ds2.dailyETTotal), ds.dailyETTotal)
    }

    private fun getHeaderBlock() =
        HeaderBlock(Array(212) { (0..255).random().toUByte() }.toUByteArray())

    private fun getWlkDayData() =
        WlkDayData(
            date = LocalDate.of(2021, 10, 22),
            dailySummary1 = getWlkDailySummary1(),
            dailySummary2 = getWlkDailySummary2(),
            weatherRecords = listOf(getWlkWeatherDataRecord())
        )

    private fun getWlkWeatherDataRecord() =
        WlkWeatherDataRecord(
            arrayOf("00000001", "01010000", "00000101", "01010000", "01010000", "00000101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "10100000", "01010000", "00000101", "01010000", "00001011", "00000110", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "10100000", "01010000", "00000101", "01010000", "00000101", "00001111", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "01110000", "10000000", "00100011")
                .map { it.toUByte(2) }
                .toUByteArray()
        )

    private fun getWlkDailySummary1() =
        DailySummary1(
            arrayOf("00000010", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "10100000", "01010000", "00000101", "01010000", "00000101", "00001111", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "01110000", "10000000", "00100011")
                .map { it.toUByte(2) }
                .toUByteArray()
        )

    private fun getWlkDailySummary2() =
        DailySummary2(
            arrayOf("00000011", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "10100000", "01010000", "00000101", "01010000", "11010000", "00100101", "00000000", "00100000", "00110101", "10110000", "01000000", "00010100", "01100000", "10100000", "00000100", "00000100", "00010000", "11000000", "00110100", "11110000", "10010000", "00010000", "00110000", "11100000", "00010010", "01110000", "10000000", "00100011")
                .map { it.toUByte(2) }
                .toUByteArray()
        )
}
