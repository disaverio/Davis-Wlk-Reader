package dev.disaverio.wlkconverter.types

class DeltaTemperature private constructor(val fahrenheit: Double) {

    val celsius: Double
        get() = fahrenheit / 1.8

    override fun toString() =
        String.format("%.2f", celsius)

    companion object {
        fun fromFahrenheit(value: Double) = DeltaTemperature(value)
    }
}