package dev.disaverio.wlkconverter.service.output

import dev.disaverio.wlkconverter.utils.readFileLines
import dev.disaverio.wlkreader.models.data.DailySummary
import dev.disaverio.wlkreader.models.data.WeatherDataRecord
import dev.disaverio.wlkreader.types.TypeWithUnitSystem
import dev.disaverio.wlkreader.types.units.UnitSystem
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

abstract class FieldsListPrinter(
    outputFieldsListFilename: String?,
    private val unitSystem: UnitSystem?
) {

    private val dailySummaryFields: List<KProperty1<DailySummary, *>>
    private val weatherDataRecordFields: List<KProperty1<WeatherDataRecord, *>>

    protected open val printDailySummaries: Boolean
        get() = dailySummaryFields.isNotEmpty()

    protected open val printDailyData: Boolean
        get() = weatherDataRecordFields.isNotEmpty()

    init {
        val propertiesFromFile = getSanitizedListOfPropertiesFromFile(outputFieldsListFilename)
        dailySummaryFields = getListOfExpectedPropertiesFromFullListOfProperties(propertiesFromFile["DS"])
        weatherDataRecordFields = getListOfExpectedPropertiesFromFullListOfProperties(propertiesFromFile["DD"])
    }

    protected open fun<T> getHeader(elements: List<T>): List<String> =
        when (elements.firstOrNull()) {
            is DailySummary -> dailySummaryFields.map { it.name }
            is WeatherDataRecord -> weatherDataRecordFields.map { it.name }
            null -> listOf()
            else -> throw Exception("Unknown element type.")
        }

    protected open fun<T> getRequestedFields(elements: List<T>): List<List<String>> =
        when (elements.firstOrNull()) {
            is DailySummary -> getRequestedFieldsBasedOnRequestedPropertiesList(elements as List<DailySummary>, dailySummaryFields)
            is WeatherDataRecord -> getRequestedFieldsBasedOnRequestedPropertiesList(elements as List<WeatherDataRecord>, weatherDataRecordFields)
            else -> throw Exception("Unknown element type.")
        }

    private fun<T> getRequestedFieldsBasedOnRequestedPropertiesList(elements: List<T>, requiredPropertiesList: List<KProperty1<T, *>>): List<List<String>> =
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

    private fun getSanitizedListOfPropertiesFromFile(outputFieldsListFilename: String?): Map<String, List<String>?> {
        val fileLines = try {
            if (outputFieldsListFilename != null) readFileLines(outputFieldsListFilename) else null
        } catch (e: Exception) {
            println("Error reading fields list file: ${e.message}.\nAll fields will be printed.")
            null
        }

        if (fileLines.isNullOrEmpty()) {
            return mapOf(
                "DS" to null,
                "DD" to null
            )
        }

        val dsProps = fileLines.singleOrNull { it.split(":").firstOrNull()?.trim()?.uppercase() == "DAILY_SUMMARY_FIELDS" }
        val ddProps = fileLines.singleOrNull { it.split(":").firstOrNull()?.trim()?.uppercase() == "DAILY_DATA_FIELDS" }

        return mapOf(
            "DS" to dsProps?.split(":")?.last()?.split(",")?.map { it.trim() },
            "DD" to ddProps?.split(":")?.last()?.split(",")?.map { it.trim() }
        )
    }
}
