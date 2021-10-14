package dev.disaverio.wlkreader.service.output

import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.utils.readFileLines
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

abstract class FieldsListPrinter(outputFieldsListFilename: String?) {

    private val dailySummaryFields: List<KProperty1<DailySummary, *>>
    private val weatherDataRecordFields: List<KProperty1<WeatherDataRecord, *>>

    init {
        val fileLines = if (outputFieldsListFilename != null) readFileLines(outputFieldsListFilename) else listOf(null, null)
        dailySummaryFields = getListOfExpectedPropertiesFromFullListOfProperties(fileLines[0]?.split(","))
        weatherDataRecordFields = getListOfExpectedPropertiesFromFullListOfProperties(fileLines[1]?.split(","))
    }

    protected open fun<T> getHeader(elements: List<T>) =
        when (elements.firstOrNull()) {
            is DailySummary -> dailySummaryFields.map { it.name }
            is WeatherDataRecord -> weatherDataRecordFields.map { it.name }
            null -> listOf()
            else -> throw Exception("Unknown element type.")
        }

    protected open fun<T> getRequestedFields(elements: List<T>) =
        when (elements.firstOrNull()) {
            is DailySummary -> getRequestedFieldsBasedOnRequestedPropertiesList(elements as List<DailySummary>, dailySummaryFields)
            is WeatherDataRecord -> getRequestedFieldsBasedOnRequestedPropertiesList(elements as List<WeatherDataRecord>, weatherDataRecordFields)
            else -> throw Exception("Unknown element type.")
        }

    private fun<T> getRequestedFieldsBasedOnRequestedPropertiesList(elements: List<T>, requiredPropertiesList: List<KProperty1<T, *>>) =
        elements.map { el -> requiredPropertiesList.map { it.get(el).toString() } }

    private inline fun<reified T: Any> getListOfExpectedPropertiesFromFullListOfProperties(namesListOfExpectedProperties: List<String>?): List<KProperty1<T, *>>  {
        val fullPropertiesList = T::class.declaredMemberProperties.toList()
        return namesListOfExpectedProperties?.filter { field -> fullPropertiesList.map { it.name }.contains(field) }?.map { field -> fullPropertiesList.find { it.name == field }!! } ?: fullPropertiesList
    }
}