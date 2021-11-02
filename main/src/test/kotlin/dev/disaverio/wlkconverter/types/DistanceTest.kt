package dev.disaverio.wlkconverter.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class DistanceTest {

    @ParameterizedTest
    @CsvSource(
        "1, 1.609344, 1609.344",
        "3.597, 5.788810368, 5788.810368",
        "0, 0, 0"
    )
    fun `fromMiles should correctly store miles and convert to other units`(miles: Double, kilometers: Double, meters: Double) {
        val distance = Distance.fromMiles(miles)

        Assert.assertEquals(miles, distance.miles, 0.000000001)
        Assert.assertEquals(kilometers, distance.kilometers, 0.000000001)
        Assert.assertEquals(meters, distance.meters, 0.000000001)
    }
}