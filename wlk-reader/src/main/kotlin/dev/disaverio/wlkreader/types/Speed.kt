package dev.disaverio.wlkreader.types

class Speed private constructor(private val length: Length): TypeWithUnitSystem<Double>() {

    val mileperhour: Double
        get() = length.mile

    val kilometreperhour: Double
        get() = length.kilometre

    val metrepersecond: Double
        get() = length.metre / 3600.0

    override fun getDefault() =
        kilometreperhour

    override fun getSi() =
        metrepersecond

    override fun getImperial() =
        mileperhour

    companion object {
        fun fromMilePerHour(value: Double) = Speed(Length.fromMile(value))
    }
}