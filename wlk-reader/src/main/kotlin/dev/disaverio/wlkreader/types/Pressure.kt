package dev.disaverio.wlkreader.types

class Pressure private constructor(val inchesofmercury: Double): TypeWithUnitSystem<Double>() {

    val hectopascal: Double
        get() = inchesofmercury * (30477498.0 / 900000.0)

    val pascal: Double
        get() = hectopascal * 100.0

    override fun getDefault() =
        hectopascal

    override fun getSi() =
        pascal

    override fun getImperial() =
        inchesofmercury

    companion object {
        fun fromInchesOfMercury(value: Double) = Pressure(value)
    }
}