package dev.disaverio.wlkreader.models.wlk

class DailySummary2(byteArray: UByteArray) {

    val dataType: UByteArray           // Array dim: 1 // must be equal to 3
    val reserved: UByteArray           // Array dim: 1 // this will cause the rest of the fields to start on an even address
    val todaysWeather: UByteArray      // Array dim: 2 // bitmapped weather conditions (Fog, T-Storm, hurricane, etc) // this field is not used now.
    val numWindPackets: UByteArray     // Array dim: 2 // # of valid packets containing wind data, // this is used to indicate reception quality
    val hiSolar: UByteArray            // Array dim: 2 // Watts per meter squared
    val dailySolarEnergy: UByteArray   // Array dim: 2 // 1/10'th Ly
    val minSunlight: UByteArray        // Array dim: 2 // number of accumulated minutes where the avg solar rad > 150
    val dailyETTotal: UByteArray       // Array dim: 2 // 1/1000'th of an inch
    val hiHeat: UByteArray             // Array dim: 2 // tenths of a degree F
    val lowHeat: UByteArray            // Array dim: 2 // tenths of a degree F
    val avgHeat: UByteArray            // Array dim: 2 // tenths of a degree F
    val hiTHSW: UByteArray             // Array dim: 2 // tenths of a degree F
    val lowTHSW: UByteArray            // Array dim: 2 // tenths of a degree F
    val hiTHW: UByteArray              // Array dim: 2 // tenths of a degree F
    val lowTHW: UByteArray             // Array dim: 2 // tenths of a degree F
    val integratedHeatDD65: UByteArray // Array dim: 2 // integrated Heating Degree Days (65F threshold) // tenths of a degree-day F
    val hiWetBulb: UByteArray          // Array dim: 2 // tenths of a degree F // Wet bulb values are not calculated
    val lowWetBulb: UByteArray         // Array dim: 2 // tenths of a degree F // Wet bulb values are not calculated
    val avgWetBulb: UByteArray         // Array dim: 2 // tenths of a degree F // Wet bulb values are not calculated
    val dirBins: UByteArray            // Array dim: 24 // space for 16 direction bins // (Used to calculate monthly dominant Dir)
    val timeValues: UByteArray         // Array dim: 15 // space for 10 time values (see below)
    val integratedCoolDD65: UByteArray // Array dim: 2 // integrated Cooling Degree Days (65F threshold) // tenths of a degree-day F
    val reserved2: UByteArray          // Array dim: 11

    init {
        require(byteArray.size == getDimension())
        require(DataType[byteArray[0].toInt()] == DataType.DAILY_SUMMARY_2)

        var offset = 0
        dataType = byteArray.sliceArray(offset until offset + DATA_TYPE_DIM)

        offset += DATA_TYPE_DIM
        reserved = byteArray.sliceArray(offset until offset + RESERVED_DIM)

        offset += RESERVED_DIM
        todaysWeather = byteArray.sliceArray(offset until offset + TODAYS_WEATHER_DIM)

        offset += TODAYS_WEATHER_DIM
        numWindPackets = byteArray.sliceArray(offset until offset + NUM_WIND_PACKETS_DIM)

        offset += NUM_WIND_PACKETS_DIM
        hiSolar = byteArray.sliceArray(offset until offset + HI_SOLAR_DIM)

        offset += HI_SOLAR_DIM
        dailySolarEnergy = byteArray.sliceArray(offset until offset + DAILY_SOLAR_ENERGY_DIM)

        offset += DAILY_SOLAR_ENERGY_DIM
        minSunlight = byteArray.sliceArray(offset until offset + MIN_SUNLIGHT_DIM)

        offset += MIN_SUNLIGHT_DIM
        dailyETTotal = byteArray.sliceArray(offset until offset + DAILY_ETTOTAL_DIM)

        offset += DAILY_ETTOTAL_DIM
        hiHeat = byteArray.sliceArray(offset until offset + HI_HEAT_DIM)

        offset += HI_HEAT_DIM
        lowHeat = byteArray.sliceArray(offset until offset + LOW_HEAT_DIM)

        offset += LOW_HEAT_DIM
        avgHeat = byteArray.sliceArray(offset until offset + AVG_HEAT_DIM)

        offset += AVG_HEAT_DIM
        hiTHSW = byteArray.sliceArray(offset until offset + HI_THSW_DIM)

        offset += HI_THSW_DIM
        lowTHSW = byteArray.sliceArray(offset until offset + LOW_THSW_DIM)

        offset += LOW_THSW_DIM
        hiTHW = byteArray.sliceArray(offset until offset + HI_THW_DIM)

        offset += HI_THW_DIM
        lowTHW = byteArray.sliceArray(offset until offset + LOW_THW_DIM)

        offset += LOW_THW_DIM
        integratedHeatDD65 = byteArray.sliceArray(offset until offset + INTEGRATED_HEAT_DD_65_DIM)

        offset += INTEGRATED_HEAT_DD_65_DIM
        hiWetBulb = byteArray.sliceArray(offset until offset + HI_WET_BULB_DIM)

        offset += HI_WET_BULB_DIM
        lowWetBulb = byteArray.sliceArray(offset until offset + LOW_WET_BULB_DIM)

        offset += LOW_WET_BULB_DIM
        avgWetBulb = byteArray.sliceArray(offset until offset + AVG_WET_BULB_DIM)

        offset += AVG_WET_BULB_DIM
        dirBins = byteArray.sliceArray(offset until offset + DIR_BINS_DIM)

        offset += DIR_BINS_DIM
        timeValues = byteArray.sliceArray(offset until offset + TIME_VALUES_DIM)

        offset += TIME_VALUES_DIM
        integratedCoolDD65 = byteArray.sliceArray(offset until offset + INTEGRATED_COOL_DD_65_DIM)

        offset += INTEGRATED_COOL_DD_65_DIM
        reserved2 = byteArray.sliceArray(offset until offset + RESERVED_2_DIM)
    }

    companion object {
        private const val DATA_TYPE_DIM = 1
        private const val RESERVED_DIM = 1
        private const val TODAYS_WEATHER_DIM = 2
        private const val NUM_WIND_PACKETS_DIM = 2
        private const val HI_SOLAR_DIM = 2
        private const val DAILY_SOLAR_ENERGY_DIM = 2
        private const val MIN_SUNLIGHT_DIM = 2
        private const val DAILY_ETTOTAL_DIM = 2
        private const val HI_HEAT_DIM = 2
        private const val LOW_HEAT_DIM = 2
        private const val AVG_HEAT_DIM = 2
        private const val HI_THSW_DIM = 2
        private const val LOW_THSW_DIM = 2
        private const val HI_THW_DIM = 2
        private const val LOW_THW_DIM = 2
        private const val INTEGRATED_HEAT_DD_65_DIM = 2
        private const val HI_WET_BULB_DIM = 2
        private const val LOW_WET_BULB_DIM = 2
        private const val AVG_WET_BULB_DIM = 2
        private const val DIR_BINS_DIM = 24
        private const val TIME_VALUES_DIM = 15
        private const val INTEGRATED_COOL_DD_65_DIM = 2
        private const val RESERVED_2_DIM = 11

        fun getDimension() =
            DATA_TYPE_DIM + RESERVED_DIM + TODAYS_WEATHER_DIM + NUM_WIND_PACKETS_DIM + HI_SOLAR_DIM + DAILY_SOLAR_ENERGY_DIM + MIN_SUNLIGHT_DIM + DAILY_ETTOTAL_DIM + HI_HEAT_DIM + LOW_HEAT_DIM + AVG_HEAT_DIM + HI_THSW_DIM + LOW_THSW_DIM + HI_THW_DIM + LOW_THW_DIM + INTEGRATED_HEAT_DD_65_DIM + HI_WET_BULB_DIM + LOW_WET_BULB_DIM + AVG_WET_BULB_DIM + DIR_BINS_DIM + TIME_VALUES_DIM + INTEGRATED_COOL_DD_65_DIM + RESERVED_2_DIM
    }
}