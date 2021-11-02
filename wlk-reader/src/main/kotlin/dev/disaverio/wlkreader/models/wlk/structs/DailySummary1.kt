package dev.disaverio.wlkreader.models.wlk.structs

import dev.disaverio.wlkreader.models.wlk.DataType

class DailySummary1(byteArray: UByteArray) {

    val dataType: UByteArray          // Array dim: 1 // must be equal to 2
    val reserved: UByteArray          // Array dim: 1 // this will cause the rest of the fields to start on an even address
    val dataSpan: UByteArray          // Array dim: 2 // total # of minutes accounted for by physical records for this day
    val hiOutTemp: UByteArray         // Array dim: 2 // tenths of a degree F
    val lowOutTemp: UByteArray        // Array dim: 2 // tenths of a degree F
    val hiInTemp: UByteArray          // Array dim: 2 // tenths of a degree F
    val lowInTemp: UByteArray         // Array dim: 2 // tenths of a degree F
    val avgOutTemp: UByteArray        // Array dim: 2 // tenths of a degree F (integrated over the day)
    val avgInTemp: UByteArray         // Array dim: 2 // tenths of a degree F (integrated over the day)
    val hiChill: UByteArray           // Array dim: 2 // tenths of a degree F
    val lowChill: UByteArray          // Array dim: 2 // tenths of a degree F
    val hiDew: UByteArray             // Array dim: 2 // tenths of a degree F
    val lowDew: UByteArray            // Array dim: 2 // tenths of a degree F
    val avgChill: UByteArray          // Array dim: 2 // tenths of a degree F
    val avgDew: UByteArray            // Array dim: 2 // tenths of a degree F
    val hiOutHum: UByteArray          // Array dim: 2 // tenths of a percent
    val lowOutHum: UByteArray         // Array dim: 2 // tenths of a percent
    val hiInHum: UByteArray           // Array dim: 2 // tenths of a percent
    val lowInHum: UByteArray          // Array dim: 2 // tenths of a percent
    val avgOutHum: UByteArray         // Array dim: 2 // tenths of a percent
    val hiBar: UByteArray             // Array dim: 2 // thousandths of an inch Hg
    val lowBar: UByteArray            // Array dim: 2 // thousandths of an inch Hg
    val avgBar: UByteArray            // Array dim: 2 // thousandths of an inch Hg
    val hiSpeed: UByteArray           // Array dim: 2 // tenths of an MPH
    val avgSpeed: UByteArray          // Array dim: 2 // tenths of an MPH
    val dailyWindRunTotal: UByteArray // Array dim: 2 // 1/10'th of an mile
    val hi10MinSpeed: UByteArray      // Array dim: 2 // the highest average wind speed record
    val dirHiSpeed: UByteArray        // Array dim: 1 // direction code (0-15, 255)
    val hi10MinDir: UByteArray        // Array dim: 1 // direction code (0-15, 255)
    val dailyRainTotal: UByteArray    // Array dim: 2 // 1/1000'th of an inch
    val hiRainRate: UByteArray        // Array dim: 2 // 1/100'th inch/hr ???
    val dailyUVDose: UByteArray       // Array dim: 2 // 1/10'th of a standard MED
    val hiUV: UByteArray              // Array dim: 1 // tenth of a UV Index
    val timeValues: UByteArray        // Array dim: 27 // space for 18 time values

    init {
        require(byteArray.size == getDimension())
        require(DataType[byteArray[0].toInt()] == DataType.DAILY_SUMMARY_1)

        var offset = 0
        dataType = byteArray.sliceArray(offset until offset + DATA_TYPE_DIM)

        offset += DATA_TYPE_DIM
        reserved = byteArray.sliceArray(offset until offset + RESERVED_DIM)

        offset += RESERVED_DIM
        dataSpan = byteArray.sliceArray(offset until offset + DATA_SPAN_DIM)

        offset += DATA_SPAN_DIM
        hiOutTemp = byteArray.sliceArray(offset until offset + HI_OUT_TEMP_DIM)

        offset += HI_OUT_TEMP_DIM
        lowOutTemp = byteArray.sliceArray(offset until offset + LOW_OUT_TEMP_DIM)

        offset += LOW_OUT_TEMP_DIM
        hiInTemp = byteArray.sliceArray(offset until offset + HI_IN_TEMP_DIM)

        offset += HI_IN_TEMP_DIM
        lowInTemp = byteArray.sliceArray(offset until offset + LOW_IN_TEMP_DIM)

        offset += LOW_IN_TEMP_DIM
        avgOutTemp = byteArray.sliceArray(offset until offset + AVG_OUT_TEMP_DIM)

        offset += AVG_OUT_TEMP_DIM
        avgInTemp = byteArray.sliceArray(offset until offset + AVG_IN_TEMP_DIM)

        offset += AVG_IN_TEMP_DIM
        hiChill = byteArray.sliceArray(offset until offset + HI_CHILL_DIM)

        offset += HI_CHILL_DIM
        lowChill = byteArray.sliceArray(offset until offset + LOW_CHILL_DIM)

        offset += LOW_CHILL_DIM
        hiDew = byteArray.sliceArray(offset until offset + HI_DEW_DIM)

        offset += HI_DEW_DIM
        lowDew = byteArray.sliceArray(offset until offset + LOW_DEW_DIM)

        offset += LOW_DEW_DIM
        avgChill = byteArray.sliceArray(offset until offset + AVG_CHILL_DIM)

        offset += AVG_CHILL_DIM
        avgDew = byteArray.sliceArray(offset until offset + AVG_DEW_DIM)

        offset += AVG_DEW_DIM
        hiOutHum = byteArray.sliceArray(offset until offset + HI_OUT_HUM_DIM)

        offset += HI_OUT_HUM_DIM
        lowOutHum = byteArray.sliceArray(offset until offset + LOW_OUT_HUM_DIM)

        offset += LOW_OUT_HUM_DIM
        hiInHum = byteArray.sliceArray(offset until offset + HI_IN_HUM_DIM)

        offset += HI_IN_HUM_DIM
        lowInHum = byteArray.sliceArray(offset until offset + LOW_IN_HUM_DIM)

        offset += LOW_IN_HUM_DIM
        avgOutHum = byteArray.sliceArray(offset until offset + AVG_OUT_HUM_DIM)

        offset += AVG_OUT_HUM_DIM
        hiBar = byteArray.sliceArray(offset until offset + HI_BAR_DIM)

        offset += HI_BAR_DIM
        lowBar = byteArray.sliceArray(offset until offset + LOW_BAR_DIM)

        offset += LOW_BAR_DIM
        avgBar = byteArray.sliceArray(offset until offset + AVG_BAR_DIM)

        offset += AVG_BAR_DIM
        hiSpeed = byteArray.sliceArray(offset until offset + HI_SPEED_DIM)

        offset += HI_SPEED_DIM
        avgSpeed = byteArray.sliceArray(offset until offset + AVG_SPEED_DIM)

        offset += AVG_SPEED_DIM
        dailyWindRunTotal = byteArray.sliceArray(offset until offset + DAILY_WIND_RUN_TOTAL_DIM)

        offset += DAILY_WIND_RUN_TOTAL_DIM
        hi10MinSpeed = byteArray.sliceArray(offset until offset + HI_10_MIN_SPEED_DIM)

        offset += HI_10_MIN_SPEED_DIM
        dirHiSpeed = byteArray.sliceArray(offset until offset + DIR_HI_SPEED_DIM)

        offset += DIR_HI_SPEED_DIM
        hi10MinDir = byteArray.sliceArray(offset until offset + HI_10_MIN_DIR_DIM)

        offset += HI_10_MIN_DIR_DIM
        dailyRainTotal = byteArray.sliceArray(offset until offset + DAILY_RAIN_TOTAL_DIM)

        offset += DAILY_RAIN_TOTAL_DIM
        hiRainRate = byteArray.sliceArray(offset until offset + HI_RAIN_RATE_DIM)

        offset += HI_RAIN_RATE_DIM
        dailyUVDose = byteArray.sliceArray(offset until offset + DAILY_UVDOSE_DIM)

        offset += DAILY_UVDOSE_DIM
        hiUV = byteArray.sliceArray(offset until offset + HI_UV_DIM)

        offset += HI_UV_DIM
        timeValues = byteArray.sliceArray(offset until offset + TIME_VALUES_DIM)
    }

    companion object {
        private const val DATA_TYPE_DIM = 1
        private const val RESERVED_DIM = 1
        private const val DATA_SPAN_DIM = 2
        private const val HI_OUT_TEMP_DIM = 2
        private const val LOW_OUT_TEMP_DIM = 2
        private const val HI_IN_TEMP_DIM = 2
        private const val LOW_IN_TEMP_DIM = 2
        private const val AVG_OUT_TEMP_DIM = 2
        private const val AVG_IN_TEMP_DIM = 2
        private const val HI_CHILL_DIM = 2
        private const val LOW_CHILL_DIM = 2
        private const val HI_DEW_DIM = 2
        private const val LOW_DEW_DIM = 2
        private const val AVG_CHILL_DIM = 2
        private const val AVG_DEW_DIM = 2
        private const val HI_OUT_HUM_DIM = 2
        private const val LOW_OUT_HUM_DIM = 2
        private const val HI_IN_HUM_DIM = 2
        private const val LOW_IN_HUM_DIM = 2
        private const val AVG_OUT_HUM_DIM = 2
        private const val HI_BAR_DIM = 2
        private const val LOW_BAR_DIM = 2
        private const val AVG_BAR_DIM = 2
        private const val HI_SPEED_DIM = 2
        private const val AVG_SPEED_DIM = 2
        private const val DAILY_WIND_RUN_TOTAL_DIM = 2
        private const val HI_10_MIN_SPEED_DIM = 2
        private const val DIR_HI_SPEED_DIM = 1
        private const val HI_10_MIN_DIR_DIM = 1
        private const val DAILY_RAIN_TOTAL_DIM = 2
        private const val HI_RAIN_RATE_DIM = 2
        private const val DAILY_UVDOSE_DIM = 2
        private const val HI_UV_DIM = 1
        private const val TIME_VALUES_DIM = 27

        fun getDimension() =
            DATA_TYPE_DIM + RESERVED_DIM + DATA_SPAN_DIM + HI_OUT_TEMP_DIM + LOW_OUT_TEMP_DIM + HI_IN_TEMP_DIM + LOW_IN_TEMP_DIM + AVG_OUT_TEMP_DIM + AVG_IN_TEMP_DIM + HI_CHILL_DIM + LOW_CHILL_DIM + HI_DEW_DIM + LOW_DEW_DIM + AVG_CHILL_DIM + AVG_DEW_DIM + HI_OUT_HUM_DIM + LOW_OUT_HUM_DIM + HI_IN_HUM_DIM + LOW_IN_HUM_DIM + AVG_OUT_HUM_DIM + HI_BAR_DIM + LOW_BAR_DIM + AVG_BAR_DIM + HI_SPEED_DIM + AVG_SPEED_DIM + DAILY_WIND_RUN_TOTAL_DIM + HI_10_MIN_SPEED_DIM + DIR_HI_SPEED_DIM + HI_10_MIN_DIR_DIM + DAILY_RAIN_TOTAL_DIM + HI_RAIN_RATE_DIM + DAILY_UVDOSE_DIM + HI_UV_DIM + TIME_VALUES_DIM
    }
}