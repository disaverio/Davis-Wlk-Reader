package dev.disaverio.wlkreader.types

class Precipitation private constructor(private val _inches: Double? = null, private val _mm: Double? = null) {

    val inches: Double
        get() = _inches ?: (mm / 25.4)

    val mm: Double
        get() = _mm ?: (inches * 25.4)

    override fun toString() =
        String.format("%.2f", mm)

    companion object {
        fun fromInches(value: Double) = Precipitation(_inches = value)
        fun fromMm(value: Double) = Precipitation(_mm = value)
    }
}