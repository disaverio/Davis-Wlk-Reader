package dev.disaverio.wlkreader.models.wlk

import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataTypeTest {

    @Test
    fun `WEATHER_DATA_RECORD code should be equal to 1`() =
        assertEquals(1, DataType.WEATHER_DATA_RECORD.wlkCode)

    @Test
    fun `DAILY_SUMMARY_1 code should be equal to 2`() =
        assertEquals(2, DataType.DAILY_SUMMARY_1.wlkCode)

    @Test
    fun `DAILY_SUMMARY_2 code should be equal to 3`() =
        assertEquals(3, DataType.DAILY_SUMMARY_2.wlkCode)

    @ParameterizedTest
    @MethodSource("getDataTypeTestArguments")
    fun `DataType should return right type, based on provided code`(wlkCode: Int, dataType: DataType) {
        Assert.assertEquals(dataType, DataType[wlkCode])
    }

    @Test
    fun `DataType should throw when provided wlkCode is invalid`() {
        val invalidWlkCode = 123
        val ex = assertThrows<Exception> { DataType[invalidWlkCode] }

        Assertions.assertEquals("Invalid data type code: $invalidWlkCode", ex.message)
    }

    private fun getDataTypeTestArguments(): Stream<Arguments> =
        Stream.of(
            Arguments.of(1, DataType.WEATHER_DATA_RECORD),
            Arguments.of(2, DataType.DAILY_SUMMARY_1),
            Arguments.of(3, DataType.DAILY_SUMMARY_2)
        )
}