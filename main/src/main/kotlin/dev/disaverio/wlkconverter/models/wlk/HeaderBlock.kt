package dev.disaverio.wlkconverter.models.wlk

class HeaderBlock(byteArray: UByteArray) {

    val idCode: UByteArray        // Array dim: 16
    val totalRecords: UByteArray  // Array dim: 4
    val dayIndex: List<DayIndex>  // Array dim: 32 // index records for each day. Index 0 is not used (i.e. the 1'st is at index 1, not index 0)

    init {
        require(byteArray.size == getDimension())

        var offset = 0
        idCode = byteArray.sliceArray(offset until offset + ID_CODE_DIM)

        offset += ID_CODE_DIM
        totalRecords = byteArray.sliceArray(offset until offset + TOTAL_RECORDS_DIM)

        offset += TOTAL_RECORDS_DIM
        dayIndex = getDayIndexList(byteArray.sliceArray(offset until offset + DAY_INDEX_LIST_DIM * DayIndex.getDimension()))
    }

    private fun getDayIndexList(byteArray: UByteArray): List<DayIndex> {
        val result = mutableListOf<DayIndex>()

        for (i in 0 until DAY_INDEX_LIST_DIM) {
            result.add(DayIndex(byteArray.sliceArray(i * DayIndex.getDimension() until (i + 1) * DayIndex.getDimension())))
        }

        return result
    }

    companion object {
        private const val ID_CODE_DIM = 16
        private const val TOTAL_RECORDS_DIM = 4
        private const val DAY_INDEX_LIST_DIM = 32

        fun getDimension() =
            ID_CODE_DIM + TOTAL_RECORDS_DIM + DAY_INDEX_LIST_DIM * DayIndex.getDimension()
    }
}