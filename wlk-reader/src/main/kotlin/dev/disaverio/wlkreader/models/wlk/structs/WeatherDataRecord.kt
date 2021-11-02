package dev.disaverio.wlkreader.models.wlk.structs

import dev.disaverio.wlkreader.models.wlk.DataType

class WeatherDataRecord(byteArray: UByteArray) {

    val dataType: UByteArray        // Array dim: 1 // must be equal to 1
    val archiveInterval: UByteArray // Array dim: 1 // number of minutes in the archive
    val iconFlags: UByteArray       // Array dim: 1 // Icon associated with this record, plus Edit flags
    val moreFlags: UByteArray       // Array dim: 1 // Tx Id, etc.
    val packedTime: UByteArray      // Array dim: 2 // minutes past midnight of the end of the archive period
    val outsideTemp: UByteArray     // Array dim: 2 // tenths of a degree F
    val hiOutsideTemp: UByteArray   // Array dim: 2 // tenths of a degree F
    val lowOutsideTemp: UByteArray  // Array dim: 2 // tenths of a degree F
    val insideTemp: UByteArray      // Array dim: 2 // tenths of a degree F
    val barometer: UByteArray       // Array dim: 2 // thousandths of an inch Hg
    val outsideHum: UByteArray      // Array dim: 2 // tenths of a percent
    val insideHum: UByteArray       // Array dim: 2 // tenths of a percent
    val rain: UByteArray            // Array dim: 2 // number of clicks + rain collector type code
    val hiRainRate: UByteArray      // Array dim: 2 // clicks per hour
    val windSpeed: UByteArray       // Array dim: 2 // tenths of an MPH
    val hiWindSpeed: UByteArray     // Array dim: 2 // tenths of an MPH
    val windDirection: UByteArray   // Array dim: 1 // direction code (0-15, 255)
    val hiWindDirection: UByteArray // Array dim: 1 // direction code (0-15, 255)
    val numWindSamples: UByteArray  // Array dim: 2 // number of valid ISS packets containing wind data // this is a good indication of reception
    val solarRad: UByteArray        // Array dim: 2 // Watts per meter squared
    val hisolarRad: UByteArray      // Array dim: 2 // Watts per meter squared
    val UV: UByteArray              // Array dim: 1 // tenth of a UV Index
    val hiUV: UByteArray            // Array dim: 1 // tenth of a UV Index
    val leafTemp: UByteArray        // Array dim: 4 // (whole degrees F) + 90
    val extraRad: UByteArray        // Array dim: 2 // used to calculate extra heating effects of the sun in THSW index
    val newSensors: UByteArray      // Array dim: 12 // reserved for future use
    val forecast: UByteArray        // Array dim: 1 // forecast code during the archive interval
    val ET: UByteArray              // Array dim: 1 // in thousandths of an inch
    val soilTemp: UByteArray        // Array dim: 6 // (whole degrees F) + 90
    val soilMoisture: UByteArray    // Array dim: 6 // centibars of dryness
    val leafWetness: UByteArray     // Array dim: 4 // Leaf Wetness code (0-15, 255)
    val extraTemp: UByteArray       // Array dim: 7 // (whole degrees F) + 90
    val extraHum: UByteArray        // Array dim: 7 // whole percent

    init {
        require(byteArray.size == getDimension())
        require(DataType[byteArray[0].toInt()] == DataType.WEATHER_DATA_RECORD)

        var offset = 0
        dataType = byteArray.sliceArray(offset until offset + DATA_TYPE_DIM)

        offset += DATA_TYPE_DIM
        archiveInterval = byteArray.sliceArray(offset until offset + ARCHIVE_INTERVAL_DIM)

        offset += ARCHIVE_INTERVAL_DIM
        iconFlags = byteArray.sliceArray(offset until offset + ICON_FLAGS_DIM)

        offset += ICON_FLAGS_DIM
        moreFlags = byteArray.sliceArray(offset until offset + MORE_FLAGS_DIM)

        offset += MORE_FLAGS_DIM
        packedTime = byteArray.sliceArray(offset until offset + PACKED_TIME_DIM)

        offset += PACKED_TIME_DIM
        outsideTemp = byteArray.sliceArray(offset until offset + OUTSIDE_TEMP_DIM)

        offset += OUTSIDE_TEMP_DIM
        hiOutsideTemp = byteArray.sliceArray(offset until offset + HI_OUTSIDE_TEMP_DIM)

        offset += HI_OUTSIDE_TEMP_DIM
        lowOutsideTemp = byteArray.sliceArray(offset until offset + LOW_OUTSIDE_TEMP_DIM)

        offset += LOW_OUTSIDE_TEMP_DIM
        insideTemp = byteArray.sliceArray(offset until offset + INSIDE_TEMP_DIM)

        offset += INSIDE_TEMP_DIM
        barometer = byteArray.sliceArray(offset until offset + BAROMETER_DIM)

        offset += BAROMETER_DIM
        outsideHum = byteArray.sliceArray(offset until offset + OUTSIDE_HUM_DIM)

        offset += OUTSIDE_HUM_DIM
        insideHum = byteArray.sliceArray(offset until offset + INSIDE_HUM_DIM)

        offset += INSIDE_HUM_DIM
        rain = byteArray.sliceArray(offset until offset + RAIN_DIM)

        offset += RAIN_DIM
        hiRainRate = byteArray.sliceArray(offset until offset + HI_RAIN_RATE_DIM)

        offset += HI_RAIN_RATE_DIM
        windSpeed = byteArray.sliceArray(offset until offset + WIND_SPEED_DIM)

        offset += WIND_SPEED_DIM
        hiWindSpeed = byteArray.sliceArray(offset until offset + HI_WIND_SPEED_DIM)

        offset += HI_WIND_SPEED_DIM
        windDirection = byteArray.sliceArray(offset until offset + WIND_DIRECTION_DIM)

        offset += WIND_DIRECTION_DIM
        hiWindDirection = byteArray.sliceArray(offset until offset + HI_WIND_DIRECTION_DIM)

        offset += HI_WIND_DIRECTION_DIM
        numWindSamples = byteArray.sliceArray(offset until offset + NUM_WIND_SAMPLES_DIM)

        offset += NUM_WIND_SAMPLES_DIM
        solarRad = byteArray.sliceArray(offset until offset + SOLAR_RAD_DIM)

        offset += SOLAR_RAD_DIM
        hisolarRad = byteArray.sliceArray(offset until offset + HISOLAR_RAD_DIM)

        offset += HISOLAR_RAD_DIM
        UV = byteArray.sliceArray(offset until offset + UV_DIM)

        offset += UV_DIM
        hiUV = byteArray.sliceArray(offset until offset + HI_UV_DIM)

        offset += HI_UV_DIM
        leafTemp = byteArray.sliceArray(offset until offset + LEAF_TEMP_DIM)

        offset += LEAF_TEMP_DIM
        extraRad = byteArray.sliceArray(offset until offset + EXTRA_RAD_DIM)

        offset += EXTRA_RAD_DIM
        newSensors = byteArray.sliceArray(offset until offset + NEW_SENSORS_DIM)

        offset += NEW_SENSORS_DIM
        forecast = byteArray.sliceArray(offset until offset + FORECAST_DIM)

        offset += FORECAST_DIM
        ET = byteArray.sliceArray(offset until offset + ET_DIM)

        offset += ET_DIM
        soilTemp = byteArray.sliceArray(offset until offset + SOIL_TEMP_DIM)

        offset += SOIL_TEMP_DIM
        soilMoisture = byteArray.sliceArray(offset until offset + SOIL_MOISTURE_DIM)

        offset += SOIL_MOISTURE_DIM
        leafWetness = byteArray.sliceArray(offset until offset + LEAF_WETNESS_DIM)

        offset += LEAF_WETNESS_DIM
        extraTemp = byteArray.sliceArray(offset until offset + EXTRA_TEMP_DIM)

        offset += EXTRA_TEMP_DIM
        extraHum = byteArray.sliceArray(offset until offset + EXTRA_HUM_DIM)
    }

    companion object {
        private const val DATA_TYPE_DIM = 1
        private const val ARCHIVE_INTERVAL_DIM = 1
        private const val ICON_FLAGS_DIM = 1
        private const val MORE_FLAGS_DIM = 1
        private const val PACKED_TIME_DIM = 2
        private const val OUTSIDE_TEMP_DIM = 2
        private const val HI_OUTSIDE_TEMP_DIM = 2
        private const val LOW_OUTSIDE_TEMP_DIM = 2
        private const val INSIDE_TEMP_DIM = 2
        private const val BAROMETER_DIM = 2
        private const val OUTSIDE_HUM_DIM = 2
        private const val INSIDE_HUM_DIM = 2
        private const val RAIN_DIM = 2
        private const val HI_RAIN_RATE_DIM = 2
        private const val WIND_SPEED_DIM = 2
        private const val HI_WIND_SPEED_DIM = 2
        private const val WIND_DIRECTION_DIM = 1
        private const val HI_WIND_DIRECTION_DIM = 1
        private const val NUM_WIND_SAMPLES_DIM = 2
        private const val SOLAR_RAD_DIM = 2
        private const val HISOLAR_RAD_DIM = 2
        private const val UV_DIM = 1
        private const val HI_UV_DIM = 1
        private const val LEAF_TEMP_DIM = 4
        private const val EXTRA_RAD_DIM = 2
        private const val NEW_SENSORS_DIM = 12
        private const val FORECAST_DIM = 1
        private const val ET_DIM = 1
        private const val SOIL_TEMP_DIM = 6
        private const val SOIL_MOISTURE_DIM = 6
        private const val LEAF_WETNESS_DIM = 4
        private const val EXTRA_TEMP_DIM = 7
        private const val EXTRA_HUM_DIM = 7

        fun getDimension() =
            DATA_TYPE_DIM + ARCHIVE_INTERVAL_DIM + ICON_FLAGS_DIM + MORE_FLAGS_DIM + PACKED_TIME_DIM + OUTSIDE_TEMP_DIM + HI_OUTSIDE_TEMP_DIM + LOW_OUTSIDE_TEMP_DIM + INSIDE_TEMP_DIM + BAROMETER_DIM + OUTSIDE_HUM_DIM + INSIDE_HUM_DIM + RAIN_DIM + HI_RAIN_RATE_DIM + WIND_SPEED_DIM + HI_WIND_SPEED_DIM + WIND_DIRECTION_DIM + HI_WIND_DIRECTION_DIM + NUM_WIND_SAMPLES_DIM + SOLAR_RAD_DIM + HISOLAR_RAD_DIM + UV_DIM + HI_UV_DIM + LEAF_TEMP_DIM + EXTRA_RAD_DIM + NEW_SENSORS_DIM + FORECAST_DIM + ET_DIM + SOIL_TEMP_DIM + SOIL_MOISTURE_DIM + LEAF_WETNESS_DIM + EXTRA_TEMP_DIM + EXTRA_HUM_DIM
    }
}