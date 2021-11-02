package dev.disaverio.wlkconverter.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PressureTest {

    @ParameterizedTest
    @CsvSource(
        "1, 33.863886666",
        "3.597, 121.808400339",
        "0, 0, 0"
    )
    fun `fromInHg should correctly store inHg and convert to other units`(inHg: Double, hPa: Double) {
        val pressure = Pressure.fromInHg(inHg)

        Assert.assertEquals(inHg, pressure.inHg, 0.000000001)
        Assert.assertEquals(hPa, pressure.hPa, 0.000000001)
    }
}