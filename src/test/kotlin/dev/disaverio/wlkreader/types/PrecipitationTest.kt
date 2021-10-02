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
    fun `fromInches should correctly store inches and convert to other units`(inches: Double, mm: Double) {
        val precipitation = Precipitation.fromInches(inches)

        Assert.assertEquals(inches, precipitation.inches, 0.000000001)
        Assert.assertEquals(mm, precipitation.mm, 0.000000001)
    }

    @ParameterizedTest
    @CsvSource(
        "1, 25.4",
        "3.597, 91.3638",
        "0, 0"
    )
    fun `fromMm should correctly store mm and convert to other units`(inches: Double, mm: Double) {
        val precipitation = Precipitation.fromMm(mm)

        Assert.assertEquals(mm, precipitation.mm, 0.000000001)
        Assert.assertEquals(inches, precipitation.inches, 0.000000001)
    }
}