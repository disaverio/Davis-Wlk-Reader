package dev.disaverio.wlkreader.service.output

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import java.io.File
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

abstract class FieldsListPrinter(outputFieldsListFilename: String?) {

    protected val dailySummaryFields: List<KProperty1<DailySummary, *>>
    protected val weatherDataRecordFields: List<KProperty1<WeatherDataRecord, *>>

    init {
        val fileLines = if (outputFieldsListFilename != null) File(outputFieldsListFilename).readLines() else listOf(null, null)
        dailySummaryFields = getListOfExpectedPropertiesFromFullListOfProperties(fileLines[0]?.split(","))
        weatherDataRecordFields = getListOfExpectedPropertiesFromFullListOfProperties(fileLines[1]?.split(","))
    }

    protected inline fun<reified T> getHeader() =
        when (T::class) {
            DailySummary::class -> dailySummaryFields.map { it.name }
            WeatherDataRecord::class -> weatherDataRecordFields.map { it.name }
            else -> throw Exception("Unknown record type.")
        }

    protected fun<T> getRequestedFields(elements: List<T>) =
        when (elements.firstOrNull()) {
            is DailySummary -> getRequestedFieldsBasedOnRequestedPropertiesList(elements, dailySummaryFields as List<KProperty1<T, *>>)
            is WeatherDataRecord -> getRequestedFieldsBasedOnRequestedPropertiesList(elements, weatherDataRecordFields as List<KProperty1<T, *>>)
            else -> throw Exception("Unknown record type.")
        }

    private fun<T> getRequestedFieldsBasedOnRequestedPropertiesList(elements: List<T>, requiredPropertiesList: List<KProperty1<T, *>>) =
        elements.map { el -> requiredPropertiesList.map { it.get(el).toString() } }

    private inline fun<reified T: Any> getListOfExpectedPropertiesFromFullListOfProperties(namesListOfExpectedProperties: List<String>?): List<KProperty1<T, *>>  {
        val fullPropertiesList = T::class.declaredMemberProperties.toList()
        return namesListOfExpectedProperties?.filter { field -> fullPropertiesList.map { it.name }.contains(field) }?.map { field -> fullPropertiesList.find { it.name == field }!! } ?: fullPropertiesList
    }
}