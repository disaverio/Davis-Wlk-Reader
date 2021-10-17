package dev.disaverio.wlkreader.models.wlk.structs

import dev.disaverio.wlkreader.models.wlk.DataType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeatherDataRecordTest {

    private val wlkValue = getWeatherDataRecordByteArray()
    private val ds2 = WeatherDataRecord(wlkValue)

    @Test
    fun `should be 88 bytes in size`() =
        assertEquals(88, WeatherDataRecord.getDimension())

    @Test
    fun `should reject byte arrays of a size other than the one declared`() {
        repeat(100) { size ->
            if (size != WeatherDataRecord.getDimension()) {
                assertThrows<Exception> { WeatherDataRecord(getWeatherDataRecordByteArray(size = size)) }
            } else {
                assertDoesNotThrow { WeatherDataRecord(getWeatherDataRecordByteArray(size = size)) }
            }
        }
    }

    @Test
    fun `should reject byte arrays where first byte does not contain WEATHER_DATA_RECORD value`() {
        repeat(255) { firstByteValue ->
            if (firstByteValue != DataType.WEATHER_DATA_RECORD.wlkCode) {
                assertThrows<Exception> { WeatherDataRecord(getWeatherDataRecordByteArray(firstByteValue = firstByteValue)) }
            } else {
                assertDoesNotThrow { WeatherDataRecord(getWeatherDataRecordByteArray(firstByteValue = firstByteValue)) }
            }
        }
    }

    @Test
    fun `dataType should contains bytes of position 0 until 1`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(0 until 1), ds2.dataType)

    @Test
    fun `archiveInterval should contains bytes of position 1 until 2`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(1 until 2), ds2.archiveInterval)

    @Test
    fun `iconFlags should contains bytes of position 2 until 3`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(2 until 3), ds2.iconFlags)

    @Test
    fun `moreFlags should contains bytes of position 3 until 4`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(3 until 4), ds2.moreFlags)

    @Test
    fun `packedTime should contains bytes of position 4 until 6`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(4 until 6), ds2.packedTime)

    @Test
    fun `outsideTemp should contains bytes of position 6 until 8`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(6 until 8), ds2.outsideTemp)

    @Test
    fun `hiOutsideTemp should contains bytes of position 8 until 10`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(8 until 10), ds2.hiOutsideTemp)

    @Test
    fun `lowOutsideTemp should contains bytes of position 10 until 12`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(10 until 12), ds2.lowOutsideTemp)

    @Test
    fun `insideTemp should contains bytes of position 12 until 14`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(12 until 14), ds2.insideTemp)

    @Test
    fun `barometer should contains bytes of position 14 until 16`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(14 until 16), ds2.barometer)

    @Test
    fun `outsideHum should contains bytes of position 16 until 18`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(16 until 18), ds2.outsideHum)

    @Test
    fun `insideHum should contains bytes of position 18 until 20`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(18 until 20), ds2.insideHum)

    @Test
    fun `rain should contains bytes of position 20 until 22`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(20 until 22), ds2.rain)

    @Test
    fun `hiRainRate should contains bytes of position 22 until 24`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(22 until 24), ds2.hiRainRate)

    @Test
    fun `windSpeed should contains bytes of position 24 until 26`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(24 until 26), ds2.windSpeed)

    @Test
    fun `hiWindSpeed should contains bytes of position 26 until 28`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(26 until 28), ds2.hiWindSpeed)

    @Test
    fun `windDirection should contains bytes of position 28 until 29`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(28 until 29), ds2.windDirection)

    @Test
    fun `hiWindDirection should contains bytes of position 29 until 30`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(29 until 30), ds2.hiWindDirection)

    @Test
    fun `numWindSamples should contains bytes of position 30 until 32`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(30 until 32), ds2.numWindSamples)

    @Test
    fun `solarRad should contains bytes of position 32 until 34`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(32 until 34), ds2.solarRad)

    @Test
    fun `hisolarRad should contains bytes of position 34 until 36`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(34 until 36), ds2.hisolarRad)

    @Test
    fun `UV should contains bytes of position 36 until 37`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(36 until 37), ds2.UV)

    @Test
    fun `hiUV should contains bytes of position 37 until 38`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(37 until 38), ds2.hiUV)

    @Test
    fun `leafTemp should contains bytes of position 38 until 42`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(38 until 42), ds2.leafTemp)

    @Test
    fun `extraRad should contains bytes of position 42 until 44`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(42 until 44), ds2.extraRad)

    @Test
    fun `newSensors should contains bytes of position 44 until 56`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(44 until 56), ds2.newSensors)

    @Test
    fun `forecast should contains bytes of position 56 until 57`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(56 until 57), ds2.forecast)

    @Test
    fun `ET should contains bytes of position 57 until 58`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(57 until 58), ds2.ET)

    @Test
    fun `soilTemp should contains bytes of position 58 until 64`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(58 until 64), ds2.soilTemp)

    @Test
    fun `soilMoisture should contains bytes of position 64 until 70`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(64 until 70), ds2.soilMoisture)

    @Test
    fun `leafWetness should contains bytes of position 70 until 74`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(70 until 74), ds2.leafWetness)

    @Test
    fun `extraTemp should contains bytes of position 74 until 81`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(74 until 81), ds2.extraTemp)

    @Test
    fun `extraHum should contains bytes of position 81 until 88`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(81 until 88), ds2.extraHum)


    private fun assertUByteArrayAreEqual(first: UByteArray, second: UByteArray) {
        assertEquals(first.size, second.size)
        assertTrue(first.contentEquals(second))
    }

    private fun getWeatherDataRecordByteArray(size: Int = 88, firstByteValue: Int = DataType.WEATHER_DATA_RECORD.wlkCode) =
        Array(size) { i ->
            if (i == 0) firstByteValue.toUByte() else (0..255).random().toUByte()
        }.toUByteArray()
}