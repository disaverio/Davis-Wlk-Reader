package dev.disaverio.wlkreader.service

import dev.disaverio.wlkreader.models.data.MonthData
import dev.disaverio.wlkreader.models.wlk.DayData
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary1
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary2
import dev.disaverio.wlkreader.models.wlk.structs.HeaderBlock
import dev.disaverio.wlkreader.models.wlk.structs.WeatherDataRecord
import dev.disaverio.wlkreader.utils.toInt
import dev.disaverio.wlkreader.utils.toShort
import java.io.File
import java.time.LocalDate
import java.time.YearMonth
import dev.disaverio.wlkreader.models.wlk.MonthData as WlkMonthData


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
                if (dayIndex.recordsInDay.toShort() == 0.toShort()) return@forEach

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
