package dev.disaverio.wlkreader.types

class Speed private constructor(val mph: Double) {

    val kmph: Double
        get() = mph * 1.609344

    val mps: Double
        get() = kmph / 3.6

    override fun toString() =
        String.format("%.2f", kmph)

    companion object {
        fun fromMph(value: Double) = Speed(value)
    }
}