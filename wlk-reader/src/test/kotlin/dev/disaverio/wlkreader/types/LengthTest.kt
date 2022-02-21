package dev.disaverio.wlkreader.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LengthTest {

    @ParameterizedTest
    @CsvSource(
        "1, 1.609344, 1609.344",
        "3.597, 5.788810368, 5788.810368",
        "0, 0, 0"
    )
    fun `fromMile should correctly store miles and convert to other units`(mile: Double, kilometre: Double, metre: Double) {
        val length = Length.fromMile(mile)

        Assert.assertEquals(mile, length.mile, 0.000000001)
        Assert.assertEquals(kilometre, length.kilometre, 0.000000001)
        Assert.assertEquals(metre, length.metre, 0.000000001)
    }
}