package dev.disaverio.wlkconverter.types

class Temperature private constructor(val fahrenheit: Double) {

    val celsius: Double
        get() = (fahrenheit - 32) / 1.8

    override fun toString() =
        String.format("%.2f", celsius)

    companion object {
        fun fromFahrenheit(value: Double) = Temperature(value)
    }
}