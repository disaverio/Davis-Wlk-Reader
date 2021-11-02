package dev.disaverio.wlkconverter.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class DeltaTemperatureTest {

    @ParameterizedTest
    @CsvSource(
        "18, 10",
        "523.715, 290.952777778",
        "0, 0"
    )
    fun `fromFahrenheit should correctly store fahrenheit and convert to other units`(fahrenheit: Double, celsius: Double) {
        val deltaTemperature = DeltaTemperature.fromFahrenheit(fahrenheit)

        Assert.assertEquals(fahrenheit, deltaTemperature.fahrenheit, 0.000000001)
        Assert.assertEquals(celsius, deltaTemperature.celsius, 0.000000001)
    }
}