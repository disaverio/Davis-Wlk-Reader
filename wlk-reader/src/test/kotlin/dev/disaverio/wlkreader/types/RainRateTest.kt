package dev.disaverio.wlkreader.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RainRateTest {

    @ParameterizedTest
    @CsvSource(
        "1, 25.4",
        "3.597, 91.3638",
        "0, 0"
    )
    fun `fromInchesPerHour should correctly store inchesPerHour and convert to other units`(inchesPerHour: Double, mmPerHour: Double) {
        val rainRate = RainRate.fromInchesPerHour(inchesPerHour)

        Assert.assertEquals(inchesPerHour, rainRate.inchesph, 0.000000001)
        Assert.assertEquals(mmPerHour, rainRate.mmph, 0.000000001)
    }

    @ParameterizedTest
    @CsvSource(
        "1, 25.4",
        "3.597, 91.3638",
        "0, 0"
    )
    fun `fromMmPerHour should correctly store mmPerHour and convert to other units`(inchesPerHour: Double, mmPerHour: Double) {
        val rainRate = RainRate.fromMmPerHour(mmPerHour)

        Assert.assertEquals(mmPerHour, rainRate.mmph, 0.000000001)
        Assert.assertEquals(inchesPerHour, rainRate.inchesph, 0.000000001)
    }
}