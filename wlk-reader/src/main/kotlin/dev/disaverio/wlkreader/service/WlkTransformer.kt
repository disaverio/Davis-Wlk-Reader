package dev.disaverio.wlkreader.service

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.DayData
import dev.disaverio.wlkreader.models.data.MonthData
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary1
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary2
import dev.disaverio.wlkreader.types.WindDirection
import java.time.LocalDate
import dev.disaverio.wlkreader.models.wlk.DayData as WlkDayData
import dev.disaverio.wlkreader.models.wlk.MonthData as WlkMonthData
import dev.disaverio.wlkreader.models.wlk.structs.WeatherDataRecord as WlkWeatherDataRecord

class WlkTransformer private constructor() {

    companion object {
        fun fromWlk(monthData: WlkMonthData) =
            MonthData(
                date = monthData.date,
                dailyData = (monthData.dailyData.map { fromWlk(it) }).sortedBy { it.date }
            )

        fun fromWlk(dayData: WlkDayData) =
            DayData(
                date = dayData.date,
                summary = fromWlk(dayData.date, dayData.dailySummary1, dayData.dailySummary2),
                records = (dayData.weatherRecords.map { fromWlk(dayData.date, it) }).sortedBy { it.time }
            )

        fun fromWlk(date: LocalDate, record: WlkWeatherDataRecord) =
            WeatherDataRecord(
                date = date,
                time = WlkFieldsTranslator.getRecordTime(record.packedTime),
                archiveInterval = WlkFieldsTranslator.getArchiveInterval(record.archiveInterval),
                packedTime = WlkFieldsTranslator.getDimensionlessValue(record.packedTime),
                outsideTemp = WlkFieldsTranslator.getTemperature(record.outsideTemp),
                hiOutsideTemp = WlkFieldsTranslator.getTemperature(record.hiOutsideTemp),
                lowOutsideTemp = WlkFieldsTranslator.getTemperature(record.lowOutsideTemp),
                insideTemp = WlkFieldsTranslator.getTemperature(record.insideTemp),
                barometer = WlkFieldsTranslator.getPressure(record.barometer),
                outsideHum = WlkFieldsTranslator.getHumidity(record.outsideHum),
                insideHum = WlkFieldsTranslator.getHumidity(record.insideHum),
                rain = WlkFieldsTranslator.getPrecipitationFromClicksQuantity(record.rain),
                hiRainRate = WlkFieldsTranslator.getRainRateFromClicksNumber(record.rain, record.hiRainRate),
                windSpeed = WlkFieldsTranslator.getSpeed(record.windSpeed),
                hiWindSpeed = WlkFieldsTranslator.getSpeed(record.hiWindSpeed),
                windDirection = WlkFieldsTranslator.getWindDirection(record.windDirection),
                hiWindDirection = WlkFieldsTranslator.getWindDirection(record.hiWindDirection),
                numWindSamples = WlkFieldsTranslator.getDimensionlessValueUnsigned(record.numWindSamples),
                solarRad = WlkFieldsTranslator.getDimensionlessValue(record.solarRad),
                hisolarRad = WlkFieldsTranslator.getDimensionlessValue(record.hisolarRad),
                UV = WlkFieldsTranslator.getUvIndex(record.UV),
                hiUV = WlkFieldsTranslator.getUvIndex(record.hiUV),
                extraRad = WlkFieldsTranslator.getDimensionlessValue(record.extraRad),
                ET = WlkFieldsTranslator.getEvapotranspiration(record.ET)
            )

        fun fromWlk(date: LocalDate, ds1: DailySummary1, ds2: DailySummary2) =
            DailySummary(
                date = date,
                dataSpan = WlkFieldsTranslator.getDataSpan(ds1.dataSpan),
                hiOutTemp = WlkFieldsTranslator.getTemperature(ds1.hiOutTemp),
                lowOutTemp = WlkFieldsTranslator.getTemperature(ds1.lowOutTemp),
                hiInTemp = WlkFieldsTranslator.getTemperature(ds1.hiInTemp),
                lowInTemp = WlkFieldsTranslator.getTemperature(ds1.lowInTemp),
                avgOutTemp = WlkFieldsTranslator.getTemperature(ds1.avgOutTemp),
                avgInTemp = WlkFieldsTranslator.getTemperature(ds1.avgInTemp),
                hiChill = WlkFieldsTranslator.getTemperature(ds1.hiChill),
                lowChill = WlkFieldsTranslator.getTemperature(ds1.lowChill),
                hiDew = WlkFieldsTranslator.getTemperature(ds1.hiDew),
                lowDew = WlkFieldsTranslator.getTemperature(ds1.lowDew),
                avgChill = WlkFieldsTranslator.getTemperature(ds1.avgChill),
                avgDew = WlkFieldsTranslator.getTemperature(ds1.avgDew),
                hiOutHum = WlkFieldsTranslator.getHumidity(ds1.hiOutHum),
                lowOutHum = WlkFieldsTranslator.getHumidity(ds1.lowOutHum),
                hiInHum = WlkFieldsTranslator.getHumidity(ds1.hiInHum),
                lowInHum = WlkFieldsTranslator.getHumidity(ds1.lowInHum),
                avgOutHum = WlkFieldsTranslator.getHumidity(ds1.avgOutHum),
                hiBar = WlkFieldsTranslator.getPressure(ds1.hiBar),
                lowBar = WlkFieldsTranslator.getPressure(ds1.lowBar),
                avgBar = WlkFieldsTranslator.getPressure(ds1.avgBar),
                hiSpeed = WlkFieldsTranslator.getSpeed(ds1.hiSpeed),
                avgSpeed = WlkFieldsTranslator.getSpeed(ds1.avgSpeed),
                dailyWindRunTotal = WlkFieldsTranslator.getWindRun(ds1.dailyWindRunTotal),
                hi10MinSpeed = WlkFieldsTranslator.getSpeed(ds1.hi10MinSpeed),
                dirHiSpeed = WlkFieldsTranslator.getWindDirection(ds1.dirHiSpeed),
                hi10MinDir = WlkFieldsTranslator.getWindDirection(ds1.hi10MinDir),
                dailyRainTotal = WlkFieldsTranslator.getPrecipitation(ds1.dailyRainTotal),
                hiRainRate = WlkFieldsTranslator.getRainRate(ds1.hiRainRate),
                dailyUVDose = WlkFieldsTranslator.getUvMedIndex(ds1.dailyUVDose),
                hiUV = WlkFieldsTranslator.getUvIndex(ds1.hiUV),
                hiOutTempTime = WlkFieldsTranslator.getHiOutTempTime(ds1.timeValues),
                lowOutTempTime = WlkFieldsTranslator.getLowOutTempTime(ds1.timeValues),
                hiInTempTime = WlkFieldsTranslator.getHiInTempTime(ds1.timeValues),
                lowInTempTime = WlkFieldsTranslator.getLowInTempTime(ds1.timeValues),
                hiChillTime = WlkFieldsTranslator.getHiChillTime(ds1.timeValues),
                lowChillTime = WlkFieldsTranslator.getLowChillTime(ds1.timeValues),
                hiDewTime = WlkFieldsTranslator.getHiDewTime(ds1.timeValues),
                lowDewTime = WlkFieldsTranslator.getLowDewTime(ds1.timeValues),
                hiOutHumTime = WlkFieldsTranslator.getHiOutHumTime(ds1.timeValues),
                lowOutHumTime = WlkFieldsTranslator.getLowOutHumTime(ds1.timeValues),
                hiInHumTime = WlkFieldsTranslator.getHiInHumTime(ds1.timeValues),
                lowInHumTime = WlkFieldsTranslator.getLowInHumTime(ds1.timeValues),
                hiBarTime = WlkFieldsTranslator.getHiBarTime(ds1.timeValues),
                lowBarTime = WlkFieldsTranslator.getLowBarTime(ds1.timeValues),
                hiSpeedTime = WlkFieldsTranslator.getHiSpeedTime(ds1.timeValues),
                hi10MinSpeedTime = WlkFieldsTranslator.getHi10MinSpeedTime(ds1.timeValues),
                hiRainRateTime = WlkFieldsTranslator.getHiRainRateTime(ds1.timeValues),
                hiUVTime = WlkFieldsTranslator.getHiUVTime(ds1.timeValues),
                integratedHeatDD65 = WlkFieldsTranslator.getDeltaTemperature(ds2.integratedHeatDD65),
                integratedCoolDD65 = WlkFieldsTranslator.getDeltaTemperature(ds2.integratedCoolDD65),
                hiHeat = WlkFieldsTranslator.getTemperature(ds2.hiHeat),
                lowHeat = WlkFieldsTranslator.getTemperature(ds2.lowHeat),
                avgHeat = WlkFieldsTranslator.getTemperature(ds2.avgHeat),
                hiTHSW = WlkFieldsTranslator.getTemperature(ds2.hiTHSW),
                lowTHSW = WlkFieldsTranslator.getTemperature(ds2.lowTHSW),
                hiTHW = WlkFieldsTranslator.getTemperature(ds2.hiTHW),
                lowTHW = WlkFieldsTranslator.getTemperature(ds2.lowTHW),
                hiHeatTime = WlkFieldsTranslator.getHiHeatTime(ds2.timeValues),
                lowHeatTime = WlkFieldsTranslator.getLowHeatTime(ds2.timeValues),
                hiTHSWTime = WlkFieldsTranslator.getHiTHSWTime(ds2.timeValues),
                lowTHSWTime = WlkFieldsTranslator.getLowTHSWTime(ds2.timeValues),
                hiTHWTime = WlkFieldsTranslator.getHiTHWTime(ds2.timeValues),
                lowTHWTime = WlkFieldsTranslator.getLowTHWTime(ds2.timeValues),
                minutesAsDominantDirection = WindDirection.values().associateWith { WlkFieldsTranslator.getMinutesAsDominantDirection(it, ds2.dirBins) },
                hiWetBulb = WlkFieldsTranslator.getTemperature(ds2.hiWetBulb),
                lowWetBulb = WlkFieldsTranslator.getTemperature(ds2.lowWetBulb),
                avgWetBulb = WlkFieldsTranslator.getTemperature(ds2.avgWetBulb),
                numWindPackets = WlkFieldsTranslator.getDimensionlessValueUnsigned(ds2.numWindPackets),
                hiSolar = WlkFieldsTranslator.getDimensionlessValue(ds2.hiSolar),
                dailySolarEnergy = WlkFieldsTranslator.getDimensionlessValueFromTenths(ds2.dailySolarEnergy),
                minSunlight = WlkFieldsTranslator.getDimensionlessValue(ds2.minSunlight),
                dailyETTotal = WlkFieldsTranslator.getDimensionlessValueFromThousandths(ds2.dailyETTotal)
            )
    }
}
