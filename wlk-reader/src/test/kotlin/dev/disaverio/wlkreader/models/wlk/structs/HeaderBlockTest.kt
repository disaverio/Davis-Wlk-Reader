package dev.disaverio.wlkreader.models.wlk.structs

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HeaderBlockTest {

    private val wlkValue = getHeaderBlockByteArray()
    private val headerBlock = HeaderBlock(wlkValue)

    @Test
    fun `should be 212 bytes in size`() =
        assertEquals(212, HeaderBlock.getDimension())

    @Test
    fun `should reject byte arrays of a size other than the one declared`() {
        repeat(1000) { size ->
            if (size != HeaderBlock.getDimension()) {
                assertThrows<Exception> { HeaderBlock(getHeaderBlockByteArray(size = size)) }
            } else {
                assertDoesNotThrow { HeaderBlock(getHeaderBlockByteArray(size = size)) }
            }
        }
    }

    @Test
    fun `idCode should contains bytes of position 0 until 2`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(0 until 16), headerBlock.idCode)

    @Test
    fun `totalRecords should contains bytes of position 2 until 6`() =
        assertUByteArrayAreEqual(wlkValue.sliceArray(16 until 20), headerBlock.totalRecords)

    @Test
    fun `dayIndex should contains 32 elements`() =
        assertEquals(32, headerBlock.dayIndex.size)

    private fun assertUByteArrayAreEqual(first: UByteArray, second: UByteArray) {
        assertEquals(first.size, second.size)
        assertTrue(first.contentEquals(second))
    }

    private fun getHeaderBlockByteArray(size: Int = 212) =
        Array(size) { (0..255).random().toUByte() }.toUByteArray()
}