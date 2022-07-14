package dev.disaverio.wlkreader.service

import dev.disaverio.wlkreader.types.*
import dev.disaverio.wlkreader.utils.toShort
import dev.disaverio.wlkreader.utils.toUShort
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class WlkFieldsTranslator private constructor() {

    companion object {
        fun getDimensionlessValue(value: UByteArray): Int {
            require(value.size == 2)
            return value.toShort().toInt()
        }

        fun getDimensionlessValueUnsigned(value: UByteArray): Int {
            require(value.size == 2)
            return value.toUShort().toInt()
        }

        fun getDimensionlessValueFromTenths(value: UByteArray): Double {
            require(value.size == 2)
            return getDimensionlessValue(value) / 10.0
        }

        fun getDimensionlessValueFromCents(value: UByteArray): Double {
            require(value.size == 2)
            return getDimensionlessValue(value) / 100.0
        }

        fun getDimensionlessValueFromThousandths(value: UByteArray): Double {
            require(value.size == 2)
            return getDimensionlessValue(value) / 1000.0
        }

        fun getDataSpan(dataSpan: UByteArray): Int {
            require(dataSpan.size == 2)
            return getDimensionlessValue(dataSpan)
        }

        fun getTemperature(temperature: UByteArray): Temperature? {
            require(temperature.size == 2)
            return if (temperature.toShort() == Short.MIN_VALUE) null else Temperature.fromFahrenheit(getDimensionlessValueFromTenths(temperature))
        }

        fun getHumidity(humidity: UByteArray): Double? {
            require(humidity.size == 2)
            return if (humidity.toShort() == Short.MIN_VALUE) null else getDimensionlessValueFromTenths(humidity)
        }

        fun getSpeed(speed: UByteArray): Speed? {
            require(speed.size == 2)
            return if (speed.toShort() == Short.MIN_VALUE) null else Speed.fromMilePerHour(getDimensionlessValueFromTenths(speed))
        }

        fun getWindDirection(windDirection: UByteArray): WindDirection? {
            require(windDirection.size == 1)
            val directionCode = windDirection[0].toInt()
            return if (directionCode == 255) null else WindDirection[directionCode * 22.5]
        }

        fun getWindRun(windRun: UByteArray): Length? {
            require(windRun.size == 2)
            return if (windRun.toShort() == Short.MIN_VALUE) null else Length.fromMile(getDimensionlessValueFromTenths(windRun))
        }

        fun getPressure(pressure: UByteArray): Pressure? {
            require(pressure.size == 2)
            return if (pressure.toShort() == Short.MIN_VALUE) null else Pressure.fromInchesOfMercury(getDimensionlessValueFromThousandths(pressure))
        }

        fun getUvIndex(uvIndex: UByteArray): Double {
            require(uvIndex.size == 1)
            return uvIndex[0].toInt() / 10.0
        }

        fun getUvMedIndex(uvMedIndex: UByteArray): Double? {
            require(uvMedIndex.size == 2)
            return if (uvMedIndex.toShort() == Short.MIN_VALUE) null else getDimensionlessValueFromTenths(uvMedIndex)
        }

        fun getPrecipitation(precipitation: UByteArray): Precipitation {
            require(precipitation.size == 2)
            return Precipitation.fromInch(getDimensionlessValueFromThousandths(precipitation))
        }

        fun getPrecipitationFromClicksQuantity(clicksQtyAndCollectorCode: UByteArray): Precipitation {
            require(clicksQtyAndCollectorCode.size == 2)
            val collectorType = RainCollectorType[clicksQtyAndCollectorCode.toShort().toInt() and 0xF000]
            val clicksQty = clicksQtyAndCollectorCode.toShort().toInt() and 0x0FFF
            return Precipitation.fromMillimetre(clicksQty * collectorType.dimensionInMm)
        }

        fun getRainRate(rainRate: UByteArray): RainRate {
            require(rainRate.size == 2)
            return RainRate.fromInchPerHour(getDimensionlessValueFromCents(rainRate))
        }

        fun getRainRateFromClicksNumber(clicksQtyAndCollectorCode: UByteArray, clicksPerHour: UByteArray): RainRate {
            require(clicksQtyAndCollectorCode.size == 2)
            require(clicksPerHour.size == 2)
            val collectorType = RainCollectorType[clicksQtyAndCollectorCode.toShort().toInt() and 0xF000]
            return RainRate.fromMillimetrePerHour(getDimensionlessValue(clicksPerHour) * collectorType.dimensionInMm)
        }

        fun getDeltaTemperature(temperature: UByteArray): DeltaTemperature? {
            require(temperature.size == 2)
            return if (temperature.toShort() == Short.MIN_VALUE) null else DeltaTemperature.fromFahrenheit(getDimensionlessValueFromTenths(temperature))
        }

        fun getDateTime(date: LocalDate, packedTime: UByteArray): LocalDateTime {
            require(packedTime.size == 2)
            return date.atStartOfDay().plusMinutes(getDimensionlessValue(packedTime).toLong())
        }

        fun getArchiveInterval(archiveInterval: UByteArray): Int {
            require(archiveInterval.size == 1)
            return archiveInterval[0].toInt()
        }

        fun getEvapotranspiration(evapotranspiration: UByteArray): Double {
            require(evapotranspiration.size == 1)
            return evapotranspiration[0].toInt() / 1000.0
        }

        fun getHiOutTempTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(0, date, ds1TimeValues)
        }

        fun getLowOutTempTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(1, date, ds1TimeValues)
        }

        fun getHiInTempTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(2, date, ds1TimeValues)
        }

        fun getLowInTempTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(3, date, ds1TimeValues)
        }

        fun getHiChillTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(4, date, ds1TimeValues)
        }

        fun getLowChillTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(5, date, ds1TimeValues)
        }

        fun getHiDewTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(6, date, ds1TimeValues)
        }

        fun getLowDewTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(7, date, ds1TimeValues)
        }

        fun getHiOutHumTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(8, date, ds1TimeValues)
        }

        fun getLowOutHumTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(9, date, ds1TimeValues)
        }

        fun getHiInHumTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(10, date, ds1TimeValues)
        }

        fun getLowInHumTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(11, date, ds1TimeValues)
        }

        fun getHiBarTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(12, date, ds1TimeValues)
        }

        fun getLowBarTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(13, date, ds1TimeValues)
        }

        fun getHiSpeedTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(14, date, ds1TimeValues)
        }

        fun getHi10MinSpeedTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(15, date, ds1TimeValues)
        }

        fun getHiRainRateTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(16, date, ds1TimeValues)
        }

        fun getHiUVTime(date: LocalDate, ds1TimeValues: UByteArray): LocalDateTime? {
            require(ds1TimeValues.size == 27)
            return getTimeFromTimeValues(17, date, ds1TimeValues)
        }

        fun getHiHeatTime(date: LocalDate, ds2TimeValues: UByteArray): LocalDateTime? {
            require(ds2TimeValues.size == 15)
            return getTimeFromTimeValues(1, date, ds2TimeValues)
        }

        fun getLowHeatTime(date: LocalDate, ds2TimeValues: UByteArray): LocalDateTime? {
            require(ds2TimeValues.size == 15)
            return getTimeFromTimeValues(2, date, ds2TimeValues)
        }

        fun getHiTHSWTime(date: LocalDate, ds2TimeValues: UByteArray): LocalDateTime? {
            require(ds2TimeValues.size == 15)
            return getTimeFromTimeValues(3, date, ds2TimeValues)
        }

        fun getLowTHSWTime(date: LocalDate, ds2TimeValues: UByteArray): LocalDateTime? {
            require(ds2TimeValues.size == 15)
            return getTimeFromTimeValues(4, date, ds2TimeValues)
        }

        fun getHiTHWTime(date: LocalDate, ds2TimeValues: UByteArray): LocalDateTime? {
            require(ds2TimeValues.size == 15)
            return getTimeFromTimeValues(5, date, ds2TimeValues)
        }

        fun getLowTHWTime(date: LocalDate, ds2TimeValues: UByteArray): LocalDateTime? {
            require(ds2TimeValues.size == 15)
            return getTimeFromTimeValues(6, date, ds2TimeValues)
        }

        fun getMinutesAsDominantDirection(direction: WindDirection, ds2DirBins: UByteArray): Int {
            val index = when(direction) {
                WindDirection.N -> 0
                WindDirection.NNE -> 1
                WindDirection.NE -> 2
                WindDirection.ENE -> 3
                WindDirection.E -> 4
                WindDirection.ESE -> 5
                WindDirection.SE -> 6
                WindDirection.SSE -> 7
                WindDirection.S -> 8
                WindDirection.SSW -> 9
                WindDirection.SW -> 10
                WindDirection.WSW -> 11
                WindDirection.W -> 12
                WindDirection.WNW -> 13
                WindDirection.NW -> 14
                WindDirection.NNW -> 15
            }
            return getAmountOfMinutes(index, ds2DirBins) ?: 0
        }

        private fun getTimeFromTimeValues(index: Int, date: LocalDate, timeValues: UByteArray): LocalDateTime? {
            val minutesAfterMidnight = getAmountOfMinutes(index, timeValues)
            return if (minutesAfterMidnight == null) {
                null
            } else {
                date.atStartOfDay().plusMinutes(minutesAfterMidnight.toLong())
            }
        }

        private fun getAmountOfMinutes(index: Int, minutesAmounts: UByteArray): Int? {
            val fieldIndex = index / 2 * 3
            val minutes = if (index % 2 == 0) {
                minutesAmounts[fieldIndex].toInt() + (minutesAmounts[fieldIndex + 2].toInt() and 0x0F shl 8)
            } else {
                minutesAmounts[fieldIndex + 1].toInt() + (minutesAmounts[fieldIndex + 2].toInt() and 0xF0 shl 4)
            }
            return if (minutes == 0x0FFF || minutes == 0x07FF || minutes == 0x0800) null else minutes
        }
    }
}

private enum class RainCollectorType(val wlkCode: Int, val dimensionInMm: Double) {
    INCH_0_1(0x0000, 2.54),   // 0.1 inch
    INCH_0_01(0x1000, 0.254), // 0.01 inch
    MM_0_2(0x2000, 0.2),      // 0.2 mm
    MM_1_0(0x3000, 1.0),      // 1.0 mm
    MM_0_1(0x6000, 0.1);      // 0.1 mm

    companion object {
        operator fun get(code: Int): RainCollectorType {
            val map = values().associateBy(RainCollectorType::wlkCode)
            return map[code] ?: throw Exception("Invalid rain collector code: $code")
        }
    }
}
