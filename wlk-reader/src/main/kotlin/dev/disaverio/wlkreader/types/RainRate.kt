package dev.disaverio.wlkreader.types

class RainRate private constructor(private val precipitation: Precipitation): TypeWithUnitSystem<Double>() {

    val inchperhour: Double
        get() = precipitation.inch

    val millimetreperhour: Double
        get() = precipitation.millimetre

    override fun getDefault() =
        millimetreperhour

    override fun getSi() =
        millimetreperhour

    override fun getImperial() =
        inchperhour

    companion object {
        fun fromInchPerHour(value: Double) = RainRate(Precipitation.fromInch(value))
        fun fromMillimetrePerHour(value: Double) = RainRate(Precipitation.fromMillimetre(value))
    }
}