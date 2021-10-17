package dev.disaverio.wlkreader.models.wlk


enum class DataType(val wlkCode: Int) {

    WEATHER_DATA_RECORD(1),
    DAILY_SUMMARY_1(2),
    DAILY_SUMMARY_2(3);

    companion object {
        operator fun get(code: Int): DataType {
            val map = values().associateBy(DataType::wlkCode)
            return map[code] ?: throw Exception("Invalid data type code: $code")
        }
    }
}