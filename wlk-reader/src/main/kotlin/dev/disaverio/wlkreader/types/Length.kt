package dev.disaverio.wlkreader.types

class Length private constructor(val mile: Double): TypeWithUnitSystem<Double>() {

    val kilometre: Double
        get() = mile * 1.609344

    val metre: Double
        get() = kilometre * 1000

    override fun getDefault() =
        kilometre

    override fun getSi() =
        metre

    override fun getImperial() =
        mile

    companion object {
        fun fromMile(value: Double) = Length(value)
    }
}