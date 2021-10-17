package dev.disaverio.wlkreader.models.wlk.structs

import dev.disaverio.wlkreader.models.wlk.DataType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DailySummary2Test {

    private val wlkValue = getDailySummary2ByteArray()
    private val ds2 = DailySummary2(wlkValue)

    @Test
    fun `should be 88 bytes in size`() =
        assertEquals(88, DailySummary2.getDimension())

    @Test
    fun `should reject byte arrays of a size other than the one declared`() {
        repeat(100) { size ->
            if (size != DailySummary2.getDimension()) {
                assertThrows<Exception> { DailySummary2(getDailySummary2ByteArray(size = size)) }
            } else {
                assertDoesNotThrow { DailySummary2(getDailySummary2ByteArray(size = size)) }
            }
        }
    }

    @Test
    fun `should reject byte arrays where first byte does not contain DAILY_SUMMARY_2 value`() {
        repeat(255) { firstByteValue ->
            if (firstByteValue != DataType.DAILY_SUMMARY_2.wlkCode) {
                assertThrows<Exception> { DailySummary2(getDailySummary2ByteArray(firstByteValue = firstByteValue)) }
            } else {
                assertDoesNotThrow { DailySummary2(getDailySummary2ByteArray(firstByteValue = firstByteValue)) }
            }
        }
    }

    @Test
    fun `dataType should contains bytes of position 0 until 1`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(0 until 1), ds2.dataType)

    @Test
    fun `reserved should contains bytes of position 1 until 2`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(1 until 2), ds2.reserved)

    @Test
    fun `todaysWeather should contains bytes of position 2 until 4`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(2 until 4), ds2.todaysWeather)

    @Test
    fun `numWindPackets should contains bytes of position 4 until 6`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(4 until 6), ds2.numWindPackets)

    @Test
    fun `hiSolar should contains bytes of position 6 until 8`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(6 until 8), ds2.hiSolar)

    @Test
    fun `dailySolarEnergy should contains bytes of position 8 until 10`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(8 until 10), ds2.dailySolarEnergy)

    @Test
    fun `minSunlight should contains bytes of position 10 until 12`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(10 until 12), ds2.minSunlight)

    @Test
    fun `dailyETTotal should contains bytes of position 12 until 14`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(12 until 14), ds2.dailyETTotal)

    @Test
    fun `hiHeat should contains bytes of position 14 until 16`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(14 until 16), ds2.hiHeat)

    @Test
    fun `lowHeat should contains bytes of position 16 until 18`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(16 until 18), ds2.lowHeat)

    @Test
    fun `avgHeat should contains bytes of position 18 until 20`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(18 until 20), ds2.avgHeat)

    @Test
    fun `hiTHSW should contains bytes of position 20 until 22`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(20 until 22), ds2.hiTHSW)

    @Test
    fun `lowTHSW should contains bytes of position 22 until 24`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(22 until 24), ds2.lowTHSW)

    @Test
    fun `hiTHW should contains bytes of position 24 until 26`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(24 until 26), ds2.hiTHW)

    @Test
    fun `lowTHW should contains bytes of position 26 until 28`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(26 until 28), ds2.lowTHW)

    @Test
    fun `integratedHeatDD65 should contains bytes of position 28 until 30`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(28 until 30), ds2.integratedHeatDD65)

    @Test
    fun `hiWetBulb should contains bytes of position 30 until 32`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(30 until 32), ds2.hiWetBulb)

    @Test
    fun `lowWetBulb should contains bytes of position 32 until 34`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(32 until 34), ds2.lowWetBulb)

    @Test
    fun `avgWetBulb should contains bytes of position 34 until 36`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(34 until 36), ds2.avgWetBulb)

    @Test
    fun `dirBins should contains bytes of position 36 until 60`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(36 until 60), ds2.dirBins)

    @Test
    fun `timeValues should contains bytes of position 60 until 75`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(60 until 75), ds2.timeValues)

    @Test
    fun `integratedCoolDD65 should contains bytes of position 75 until 77`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(75 until 77), ds2.integratedCoolDD65)

    @Test
    fun `reserved2 should contains bytes of position 77 until 88`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(77 until 88), ds2.reserved2)

    private fun assertUByteArrayAreEqual(first: UByteArray, second: UByteArray) {
        assertEquals(first.size, second.size)
        assertTrue(first.contentEquals(second))
    }

    private fun getDailySummary2ByteArray(size: Int = 88, firstByteValue: Int = DataType.DAILY_SUMMARY_2.wlkCode) =
        Array(size) { i ->
            if (i == 0) firstByteValue.toUByte() else (0..255).random().toUByte()
        }.toUByteArray()
}