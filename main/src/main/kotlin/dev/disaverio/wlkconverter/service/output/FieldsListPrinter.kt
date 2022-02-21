package dev.disaverio.wlkconverter.service.output

import dev.disaverio.wlkconverter.utils.readFileLines
import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.types.Temperature
import dev.disaverio.wlkreader.types.TypeWithUnitSystem
import dev.disaverio.wlkreader.types.units.UnitSystem
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

abstract class FieldsListPrinter(private val unitSystem: UnitSystem? = null, outputFieldsListFilename: String?) {

    private val dailySummaryFields: List<KProperty1<DailySummary, *>>
    private val weatherDataRecordFields: List<KProperty1<WeatherDataRecord, *>>

    protected open val printDailySummaries: Boolean
        get() = dailySummaryFields.isNotEmpty()

    protected open val printDailyData: Boolean
        get() = weatherDataRecordFields.isNotEmpty()

    init {
        val fileLines = try {
            if (outputFieldsListFilename != null) readFileLines(outputFieldsListFilename) else null
        } catch (e: Exception) {
            println("Error reading fields list file: ${e.message}.\nAll fields will be printed.")
            null
        }
        dailySummaryFields = getListOfExpectedPropertiesFromFullListOfProperties(if (fileLines == null) null else fileLines[0].split(","))
        weatherDataRecordFields = getListOfExpectedPropertiesFromFullListOfProperties(if (fileLines == null) null else fileLines[1].split(","))
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
        elements.map {
            el -> requiredPropertiesList.map {
                val field = it.get(el)
                if (field is TypeWithUnitSystem<*>) field.toString(unitSystem) else field.toString()
            }
        }

    private inline fun<reified T: Any> getListOfExpectedPropertiesFromFullListOfProperties(namesListOfExpectedProperties: List<String>?): List<KProperty1<T, *>>  {
        val fullPropertiesList = T::class.declaredMemberProperties.toList()
        return namesListOfExpectedProperties?.filter { field -> fullPropertiesList.map { it.name }.contains(field) }?.map { field -> fullPropertiesList.find { it.name == field }!! } ?: fullPropertiesList
    }
}