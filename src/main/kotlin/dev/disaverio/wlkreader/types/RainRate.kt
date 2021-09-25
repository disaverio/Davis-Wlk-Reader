package dev.disaverio.wlkreader.types

class RainRate private constructor(private val precipitation: Precipitation) {

    val inph: Double
        get() = precipitation.inches

    val mmph: Double
        get() = precipitation.mm

    override fun toString() =
        String.format("%.2f", mmph)

    companion object {
        fun fromInchesPerHour(value: Double) = RainRate(Precipitation.fromInches(value))
        fun fromMmPerHour(value: Double) = RainRate(Precipitation.fromMm(value))
    }
}