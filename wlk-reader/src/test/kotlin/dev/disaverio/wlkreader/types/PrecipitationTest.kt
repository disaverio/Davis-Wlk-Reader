package dev.disaverio.wlkreader.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PrecipitationTest {

    @ParameterizedTest
    @CsvSource(
        "1, 25.4",
        "3.597, 91.3638",
        "0, 0"
    )
    fun `fromInch should correctly store inch and convert to other units`(inch: Double, millimetre: Double) {
        val precipitation = Precipitation.fromInch(inch)

        Assert.assertEquals(inch, precipitation.inch, 0.000000001)
        Assert.assertEquals(millimetre, precipitation.millimetre, 0.000000001)
    }

    @ParameterizedTest
    @CsvSource(
        "1, 25.4",
        "3.597, 91.3638",
        "0, 0"
    )
    fun `fromMillimetre should correctly store millimetre and convert to other units`(inch: Double, millimetre: Double) {
        val precipitation = Precipitation.fromMillimetre(millimetre)

        Assert.assertEquals(millimetre, precipitation.millimetre, 0.000000001)
        Assert.assertEquals(inch, precipitation.inch, 0.000000001)
    }
}