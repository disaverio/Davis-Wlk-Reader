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
    fun `fromInchPerHour should correctly store inchperhour and convert to other units`(inchPerHour: Double, millimetrePerHour: Double) {
        val rainRate = RainRate.fromInchPerHour(inchPerHour)

        Assert.assertEquals(inchPerHour, rainRate.inchperhour, 0.000000001)
        Assert.assertEquals(millimetrePerHour, rainRate.millimetreperhour, 0.000000001)
    }

    @ParameterizedTest
    @CsvSource(
        "1, 25.4",
        "3.597, 91.3638",
        "0, 0"
    )
    fun `fromMillimetrePerHour should correctly store millimetreperhour and convert to other units`(inchPerHour: Double, millimetrePerHour: Double) {
        val rainRate = RainRate.fromMillimetrePerHour(millimetrePerHour)

        Assert.assertEquals(millimetrePerHour, rainRate.millimetreperhour, 0.000000001)
        Assert.assertEquals(inchPerHour, rainRate.inchperhour, 0.000000001)
    }
}