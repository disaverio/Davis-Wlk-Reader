package dev.disaverio.wlkreader.types

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SpeedTest {

    @ParameterizedTest
    @CsvSource(
        "1, 1.609344, 0.44704",
        "3.597, 5.788810368, 1.60800288",
        "0, 0, 0"
    )
    fun `Speed#fromMilesph should correctly store milesPerHour and convert to other units`(milesPerHour: Double, kmPerHour: Double, mPerSecond: Double) {
        val speed = Speed.fromMilesph(milesPerHour)

        Assert.assertEquals(milesPerHour, speed.milesph, 0.000000001)
        Assert.assertEquals(kmPerHour, speed.kmph, 0.000000001)
        Assert.assertEquals(mPerSecond, speed.mps, 0.000000001)
    }
}