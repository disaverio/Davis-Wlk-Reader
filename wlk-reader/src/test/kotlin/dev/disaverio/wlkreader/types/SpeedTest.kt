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
    fun `fromMilePerHour should correctly store milesPerHour and convert to other units`(milePerHour: Double, kilometrePerHour: Double, metrePerSecond: Double) {
        val speed = Speed.fromMilePerHour(milePerHour)

        Assert.assertEquals(milePerHour, speed.mileperhour, 0.000000001)
        Assert.assertEquals(kilometrePerHour, speed.kilometreperhour, 0.000000001)
        Assert.assertEquals(metrePerSecond, speed.metrepersecond, 0.000000001)
    }
}