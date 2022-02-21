package dev.disaverio.wlkreader.types

class Temperature private constructor(val fahrenheit: Double): TypeWithUnitSystem<Double>() {

    val celsius: Double
        get() = (fahrenheit - 32) / 1.8

    override fun getDefault() =
        celsius

    override fun getSi() =
        celsius

    override fun getImperial() =
        fahrenheit

    companion object {
        fun fromFahrenheit(value: Double) = Temperature(value)
    }
}