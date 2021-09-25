package dev.disaverio.wlkreader.types

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WindDirectionTest {

    @ParameterizedTest
    @MethodSource("getTestArguments")
    fun `WindDirection should return right direction, based on provided degrees`(degrees: Double, direction: WindDirection) {
        Assert.assertEquals(direction, WindDirection[degrees])
    }

    @Test
    fun `WindDirection should throw when provided degrees amount is invalid`() {
        val invalidDegreesAmount = 123.456
        val ex = assertThrows<Exception> { WindDirection[invalidDegreesAmount] }

        Assertions.assertEquals("Invalid amount of degrees for wind direction: $invalidDegreesAmount", ex.message)
    }

    private fun getTestArguments(): Stream<Arguments> =
        Stream.of(
            Arguments.of(0.0, WindDirection.N),
            Arguments.of(22.5, WindDirection.NNE),
            Arguments.of(45.0, WindDirection.NE),
            Arguments.of(67.5, WindDirection.ENE),
            Arguments.of(90.0, WindDirection.E),
            Arguments.of(112.5, WindDirection.ESE),
            Arguments.of(135.0, WindDirection.SE),
            Arguments.of(157.5, WindDirection.SSE),
            Arguments.of(180.0, WindDirection.S),
            Arguments.of(202.5, WindDirection.SSW),
            Arguments.of(225.0, WindDirection.SW),
            Arguments.of(247.5, WindDirection.WSW),
            Arguments.of(270.0, WindDirection.W),
            Arguments.of(292.5, WindDirection.WNW),
            Arguments.of(315.0, WindDirection.NW),
            Arguments.of(337.5, WindDirection.NNW)
        )
}