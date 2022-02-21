package dev.disaverio.wlkreader.types

class Precipitation private constructor(private val _inch: Double? = null, private val _millimetre: Double? = null): TypeWithUnitSystem<Double>() {

    val inch: Double
        get() = _inch ?: (millimetre / 25.4)

    val millimetre: Double
        get() = _millimetre ?: (inch * 25.4)

    override fun getDefault() =
        millimetre

    override fun getSi() =
        millimetre

    override fun getImperial() =
        inch

    companion object {
        fun fromInch(value: Double) = Precipitation(_inch = value)
        fun fromMillimetre(value: Double) = Precipitation(_millimetre = value)
    }
}