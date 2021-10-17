package dev.disaverio.wlkreader.models.wlk.structs

import dev.disaverio.wlkreader.models.wlk.DataType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DailySummary1Test {

    private val wlkValue = getDailySummary1ByteArray()
    private val ds1 = DailySummary1(wlkValue)

    @Test
    fun `should be 88 bytes in size`() =
        assertEquals(88, DailySummary1.getDimension())

    @Test
    fun `should reject byte arrays of a size other than the one declared`() {
        repeat(100) { size ->
            if (size != DailySummary1.getDimension()) {
                assertThrows<Exception> { DailySummary1(getDailySummary1ByteArray(size = size)) }
            } else {
                assertDoesNotThrow { DailySummary1(getDailySummary1ByteArray(size = size)) }
            }
        }
    }

    @Test
    fun `should reject byte arrays where first byte does not contain DAILY_SUMMARY_1 value`() {
        repeat(255) { firstByteValue ->
            if (firstByteValue != DataType.DAILY_SUMMARY_1.wlkCode) {
                assertThrows<Exception> { DailySummary1(getDailySummary1ByteArray(firstByteValue = firstByteValue)) }
            } else {
                assertDoesNotThrow { DailySummary1(getDailySummary1ByteArray(firstByteValue = firstByteValue)) }
            }
        }
    }

    @Test
    fun `dataType should contains bytes of position 0 until 1`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(0 until 1), ds1.dataType)

    @Test
    fun `reserved should contains bytes of position 1 until 2`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(1 until 2), ds1.reserved)

    @Test
    fun `dataSpan should contains bytes of position 2 until 4`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(2 until 4), ds1.dataSpan)

    @Test
    fun `hiOutTemp should contains bytes of position 4 until 6`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(4 until 6), ds1.hiOutTemp)

    @Test
    fun `lowOutTemp should contains bytes of position 6 until 8`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(6 until 8), ds1.lowOutTemp)

    @Test
    fun `hiInTemp should contains bytes of position 8 until 10`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(8 until 10), ds1.hiInTemp)

    @Test
    fun `lowInTemp should contains bytes of position 10 until 12`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(10 until 12), ds1.lowInTemp)

    @Test
    fun `avgOutTemp should contains bytes of position 12 until 14`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(12 until 14), ds1.avgOutTemp)

    @Test
    fun `avgInTemp should contains bytes of position 14 until 16`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(14 until 16), ds1.avgInTemp)

    @Test
    fun `hiChill should contains bytes of position 16 until 18`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(16 until 18), ds1.hiChill)

    @Test
    fun `lowChill should contains bytes of position 18 until 20`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(18 until 20), ds1.lowChill)

    @Test
    fun `hiDew should contains bytes of position 20 until 22`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(20 until 22), ds1.hiDew)

    @Test
    fun `lowDew should contains bytes of position 22 until 24`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(22 until 24), ds1.lowDew)

    @Test
    fun `avgChill should contains bytes of position 24 until 26`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(24 until 26), ds1.avgChill)

    @Test
    fun `avgDew should contains bytes of position 26 until 28`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(26 until 28), ds1.avgDew)

    @Test
    fun `hiOutHum should contains bytes of position 28 until 30`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(28 until 30), ds1.hiOutHum)

    @Test
    fun `lowOutHum should contains bytes of position 30 until 32`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(30 until 32), ds1.lowOutHum)

    @Test
    fun `hiInHum should contains bytes of position 32 until 34`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(32 until 34), ds1.hiInHum)

    @Test
    fun `lowInHum should contains bytes of position 34 until 36`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(34 until 36), ds1.lowInHum)

    @Test
    fun `avgOutHum should contains bytes of position 36 until 38`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(36 until 38), ds1.avgOutHum)

    @Test
    fun `hiBar should contains bytes of position 38 until 40`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(38 until 40), ds1.hiBar)

    @Test
    fun `lowBar should contains bytes of position 40 until 42`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(40 until 42), ds1.lowBar)

    @Test
    fun `avgBar should contains bytes of position 42 until 44`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(42 until 44), ds1.avgBar)

    @Test
    fun `hiSpeed should contains bytes of position 44 until 46`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(44 until 46), ds1.hiSpeed)

    @Test
    fun `avgSpeed should contains bytes of position 46 until 48`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(46 until 48), ds1.avgSpeed)

    @Test
    fun `dailyWindRunTotal should contains bytes of position 48 until 50`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(48 until 50), ds1.dailyWindRunTotal)

    @Test
    fun `hi10MinSpeed should contains bytes of position 50 until 52`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(50 until 52), ds1.hi10MinSpeed)

    @Test
    fun `dirHiSpeed should contains bytes of position 52 until 53`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(52 until 53), ds1.dirHiSpeed)

    @Test
    fun `hi10MinDir should contains bytes of position 53 until 54`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(53 until 54), ds1.hi10MinDir)

    @Test
    fun `dailyRainTotal should contains bytes of position 54 until 56`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(54 until 56), ds1.dailyRainTotal)

    @Test
    fun `hiRainRate should contains bytes of position 56 until 58`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(56 until 58), ds1.hiRainRate)

    @Test
    fun `dailyUVDose should contains bytes of position 58 until 60`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(58 until 60), ds1.dailyUVDose)

    @Test
    fun `hiUV should contains bytes of position 60 until 61`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(60 until 61), ds1.hiUV)

    @Test
    fun `timeValues should contains bytes of position 61 until 88`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(61 until 88), ds1.timeValues)

    private fun assertUByteArrayAreEqual(first: UByteArray, second: UByteArray) {
        assertEquals(first.size, second.size)
        assertTrue(first.contentEquals(second))
    }

    private fun getDailySummary1ByteArray(size: Int = 88, firstByteValue: Int = DataType.DAILY_SUMMARY_1.wlkCode) =
        Array(size) { i ->
            if (i == 0) firstByteValue.toUByte() else (0..255).random().toUByte()
        }.toUByteArray()
}