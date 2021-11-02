package dev.disaverio.wlkreader.models.wlk

enum class DataType(val wlkCode: Int) {

    DAILY_SUMMARY_1(2),
    DAILY_SUMMARY_2(3),
    WEATHER_DATA_RECORD(1);

    companion object {
        operator fun get(code: Int): DataType {
            val map = values().associateBy(DataType::wlkCode)
            return map[code] ?: throw Exception("Invalid data type code: $code")
        }
    }
}