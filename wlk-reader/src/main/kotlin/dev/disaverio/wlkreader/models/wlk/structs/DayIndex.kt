package dev.disaverio.wlkreader.models.wlk.structs

class DayIndex(byteArray: UByteArray) {

    val recordsInDay: UByteArray // Array dim: 2 // Includes any daily summary records
    val startPos: UByteArray     // Array dim: 4 // The index (starting at 0) of the first daily summary record

    init {
        require(byteArray.size == getDimension())

        var offset = 0

        recordsInDay = byteArray.sliceArray(offset until offset + RECORDS_IN_DAY_DIM)
        offset += RECORDS_IN_DAY_DIM

        startPos = byteArray.sliceArray(offset until offset + START_POS_DIM)
    }

    companion object {
        private const val RECORDS_IN_DAY_DIM = 2
        private const val START_POS_DIM = 4

        fun getDimension() =
            RECORDS_IN_DAY_DIM + START_POS_DIM
    }
}