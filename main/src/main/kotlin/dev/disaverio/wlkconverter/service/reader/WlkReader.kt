package dev.disaverio.wlkconverter.service.reader

import dev.disaverio.wlkconverter.models.wlk.*
import dev.disaverio.wlkconverter.models.wlk.MonthData as WlkMonthData
import dev.disaverio.wlkconverter.utils.toInt
import dev.disaverio.wlkconverter.utils.toShort
import dev.disaverio.wlkconverter.models.data.MonthData
import java.io.File
import java.time.LocalDate
import java.time.YearMonth


class WlkReader private constructor() {

    companion object {

        fun readMonth(year: Int, month: Int, filePath: String): MonthData {
            require(WeatherDataRecord.getDimension() == DailySummary1.getDimension())
            require(WeatherDataRecord.getDimension() == DailySummary2.getDimension())
            require(DailySummary1.getDimension() == DailySummary2.getDimension())

            val byteArray: UByteArray = File(filePath).readBytes().toUByteArray()

            val headerBlock = HeaderBlock(byteArray.sliceArray(0 until HeaderBlock.getDimension()))

            val dailyData = mutableMapOf<Int, DayData>()
            headerBlock.dayIndex.withIndex().forEach { (dayNumber, dayIndex) ->

                if (dayNumber == 0) return@forEach

                var offset = HeaderBlock.getDimension() + dayIndex.startPos.toInt() * WeatherDataRecord.getDimension()

                val ds1 = DailySummary1(byteArray.sliceArray(offset until offset + DailySummary1.getDimension()))
                offset += DailySummary1.getDimension()

                val ds2 = DailySummary2(byteArray.sliceArray(offset until offset + DailySummary2.getDimension()))
                offset += DailySummary2.getDimension()

                val wRecords = mutableMapOf<Int, WeatherDataRecord>()
                repeat (dayIndex.recordsInDay.toShort() - 2) { idx ->
                    wRecords[idx] = WeatherDataRecord(byteArray.sliceArray(offset until offset + WeatherDataRecord.getDimension()))
                    offset += WeatherDataRecord.getDimension()
                }

                dailyData[dayNumber] = DayData(
                    date = LocalDate.of(year, month, dayNumber),
                    dailySummary1 = ds1,
                    dailySummary2 = ds2,
                    weatherRecords = wRecords
                )
            }

            return WlkTransformer.fromWlk(
                WlkMonthData(
                    date = YearMonth.of(year, month),
                    headerBlock = headerBlock,
                    dailyData = dailyData
                )
            )
        }
    }
}