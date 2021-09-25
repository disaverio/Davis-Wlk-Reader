package dev.disaverio.wlkreader.types

class Distance private constructor(val miles: Double) {

    val kilometers: Double
        get() = miles * 1.609344

    val meters: Double
        get() = kilometers * 1000

    override fun toString() =
        String.format("%.2f", kilometers)

    companion object {
        fun fromMiles(value: Double) = Distance(value)
    }
}