package dev.disaverio.wlkreader.service

import dev.disaverio.wlkreader.models.data.MonthData
import dev.disaverio.wlkreader.models.data.DayData
import dev.disaverio.wlkreader.models.wlk.MonthData as WlkMonthData
import dev.disaverio.wlkreader.models.wlk.DayData as WlkDayData
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary1
import dev.disaverio.wlkreader.models.wlk.structs.DailySummary2
import dev.disaverio.wlkreader.models.wlk.structs.HeaderBlock
import dev.disaverio.wlkreader.models.wlk.structs.WeatherDataRecord
import dev.disaverio.wlkreader.utils.toInt
import dev.disaverio.wlkreader.utils.toShort
import java.io.File
import java.time.LocalDate
import java.time.YearMonth


class WlkReader private constructor() {

    companion object {
        fun readMonthlyFile(filePath: String): MonthData {
            val yearmonth = filePath.split(File.separator).last().split(".").first()
            val year = yearmonth.split("-").first().toInt()
            val month = yearmonth.split("-").last().toInt()

            val byteArray = File(filePath).readBytes().toUByteArray()

            return readMonth(year, month, byteArray)
        }

        fun readMonth(year: Int, month: Int, byteArray: UByteArray): MonthData =
            WlkTransformer.fromWlk(readMonthAsWlk(year, month, byteArray))

        fun readDay(year: Int, month: Int, day: Int, byteArray: UByteArray): DayData =
            WlkTransformer.fromWlk(readDayAsWlk(year, month, day, byteArray))

        fun readMonthAsWlk(year: Int, month: Int, byteArray: UByteArray): WlkMonthData {
            require(WeatherDataRecord.getDimension() == DailySummary1.getDimension())
            require(WeatherDataRecord.getDimension() == DailySummary2.getDimension())
            require(DailySummary1.getDimension() == DailySummary2.getDimension())

            val headerBlock = HeaderBlock(byteArray.sliceArray(0 until HeaderBlock.getDimension()))

            val dailyData = mutableListOf<WlkDayData>()
            headerBlock.dayIndex.withIndex().forEach { (dayNumber, dayIndex) ->

                if (dayNumber == 0) return@forEach
                if (dayIndex.recordsInDay.toShort() == 0.toShort()) return@forEach

                var offset = HeaderBlock.getDimension() + dayIndex.startPos.toInt() * WeatherDataRecord.getDimension()
                dailyData.add(
                    readDayAsWlk(
                        year,
                        month,
                        dayNumber,
                        byteArray.sliceArray(offset until offset + dayIndex.recordsInDay.toShort() * WeatherDataRecord.getDimension())
                    )
                )
            }

            return WlkMonthData(
                date = YearMonth.of(year, month),
                headerBlock = headerBlock,
                dailyData = dailyData.sortedBy { it.date }
            )
        }

        fun readDayAsWlk(year: Int, month: Int, day: Int, byteArray: UByteArray): WlkDayData {
            require(WeatherDataRecord.getDimension() == DailySummary1.getDimension())
            require(WeatherDataRecord.getDimension() == DailySummary2.getDimension())
            require(DailySummary1.getDimension() == DailySummary2.getDimension())

            val ds1 = DailySummary1(byteArray.sliceArray(0 until DailySummary1.getDimension()))
            var offset = DailySummary1.getDimension()

            val ds2 = DailySummary2(byteArray.sliceArray(offset until offset + DailySummary2.getDimension()))
            offset += DailySummary2.getDimension()

            val wRecords = mutableListOf<WeatherDataRecord>()
            val samplesQty = byteArray.size / WeatherDataRecord.getDimension() - 2 // '-2' since the two daily summary records has already been processed
            repeat (samplesQty) {
                wRecords.add(WeatherDataRecord(byteArray.sliceArray(offset until offset + WeatherDataRecord.getDimension())))
                offset += WeatherDataRecord.getDimension()
            }

            return WlkDayData(
                date = LocalDate.of(year, month, day),
                dailySummary1 = ds1,
                dailySummary2 = ds2,
                weatherRecords = wRecords.sortedBy { it.packedTime.toShort() }
            )
        }
    }
}
