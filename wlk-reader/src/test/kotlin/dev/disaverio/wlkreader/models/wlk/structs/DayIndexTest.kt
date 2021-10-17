package dev.disaverio.wlkreader.models.wlk.structs

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DayIndexTest {

    private val wlkValue = getDayIndexByteArray()
    private val dayIndex = DayIndex(wlkValue)

    @Test
    fun `should be 6 bytes in size`() =
        assertEquals(6, DayIndex.getDimension())

    @Test
    fun `should reject byte arrays of a size other than the one declared`() {
        repeat(100) { size ->
            if (size != DayIndex.getDimension()) {
                assertThrows<Exception> { DayIndex(getDayIndexByteArray(size = size)) }
            } else {
                assertDoesNotThrow { DayIndex(getDayIndexByteArray(size = size)) }
            }
        }
    }

    @Test
    fun `recordsInDay should contains bytes of position 0 until 2`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(0 until 2), dayIndex.recordsInDay)

    @Test
    fun `startPos should contains bytes of position 2 until 6`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(2 until 6), dayIndex.startPos)

    private fun assertUByteArrayAreEqual(first: UByteArray, second: UByteArray) {
        assertEquals(first.size, second.size)
        assertTrue(first.contentEquals(second))
    }

    private fun getDayIndexByteArray(size: Int = 6) =
        Array(size) { (0..255).random().toUByte() }.toUByteArray()
}