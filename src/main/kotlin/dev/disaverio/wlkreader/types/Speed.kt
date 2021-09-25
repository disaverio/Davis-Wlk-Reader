package dev.disaverio.wlkreader.types

class Speed private constructor(private val distance: Distance) {

    val milesph: Double
        get() = distance.miles

    val kmph: Double
        get() = distance.kilometers

    val mps: Double
        get() = distance.meters / 3600.0

    override fun toString() =
        String.format("%.2f", kmph)

    companion object {
        fun fromMilesph(value: Double) = Speed(Distance.fromMiles(value))
    }
}