package dev.disaverio.wlkreader.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PressureTest {

    @ParameterizedTest
    @CsvSource(
        "1, 33.863886666, 3386.3886666",
        "3.597, 121.808400339, 12180.8400339",
        "0, 0, 0"
    )
    fun `fromInchesOfMercury should correctly store inchesofmercury and convert to other units`(inchesofmercury: Double, hectopascal: Double, pascal: Double) {
        val pressure = Pressure.fromInchesOfMercury(inchesofmercury)

        Assert.assertEquals(inchesofmercury, pressure.inchesofmercury, 0.0000001)
        Assert.assertEquals(hectopascal, pressure.hectopascal, 0.0000001)
        Assert.assertEquals(pascal, pressure.pascal, 0.0000001)
    }
}