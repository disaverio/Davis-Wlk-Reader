package dev.disaverio.wlkreader.types

class DeltaTemperature private constructor(val fahrenheit: Double): TypeWithUnitSystem<Double>() {

    val celsius: Double
        get() = fahrenheit / 1.8

    override fun getDefault() =
        celsius

    override fun getSi() =
        celsius

    override fun getImperial() =
        fahrenheit

    companion object {
        fun fromFahrenheit(value: Double) = DeltaTemperature(value)
    }
}