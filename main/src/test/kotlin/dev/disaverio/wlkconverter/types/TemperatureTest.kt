package dev.disaverio.wlkconverter.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TemperatureTest {

    @ParameterizedTest
    @CsvSource(
        "32, 0",
        "100, 37.777777778",
        "57.894, 14.385555556"
    )
    fun `fromFahrenheit should correctly store fahrenheit and convert to other units`(fahrenheit: Double, celsius: Double) {
        val temperature = Temperature.fromFahrenheit(fahrenheit)

        Assert.assertEquals(fahrenheit, temperature.fahrenheit, 0.000000001)
        Assert.assertEquals(celsius, temperature.celsius, 0.000000001)
    }
}