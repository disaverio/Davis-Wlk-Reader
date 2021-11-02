package dev.disaverio.wlkconverter.types

class Pressure private constructor(val inHg: Double) {

    val hPa: Double
        get() = inHg * (30477498.0 / 900000.0)

    override fun toString() =
        String.format("%.2f", hPa)

    companion object {
        fun fromInHg(value: Double) = Pressure(value)
    }
}