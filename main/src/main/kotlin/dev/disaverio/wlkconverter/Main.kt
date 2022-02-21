package dev.disaverio.wlkconverter

import dev.disaverio.wlkconverter.service.Service
import dev.disaverio.wlkconverter.service.output.OutputFormat
import dev.disaverio.wlkreader.types.units.UnitSystem
import kotlinx.cli.*
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.isDirectory

fun main(args: Array<String>) {
    val parser = ArgParser("WlkConverter")
    val inputFilePathList by parser.option(ArgType.String, "input", "i", "Input file path").required().multiple()
    var outputFilePath by parser.option(ArgType.String, "output", "o", "Output file path")
    val unitSystem by parser.option(ArgType.Choice<UnitSystem>(), "unit", "u", "Unit system used for values in the printed output")
    val outputFormat by parser.option(ArgType.Choice<OutputFormat>(), "outputFormat", "f", "Format for output file").default(OutputFormat.CSV) //.multiple()
    val outputFieldsListFilePath by parser.option(ArgType.String, "fieldsListFile", "p", "File containing list of fields printed in output")

    parser.parse(args)

    val fileList = getSanitizedFileList(inputFilePathList)
    val service = Service(unitSystem, outputFilePath, outputFormat, outputFieldsListFilePath)

    println("Valid files retrieved:")
    fileList.forEach { println("  $it") }

    fileList.forEach { service.printMonth(it) }
}

fun getSanitizedFileList(inputFilePathList: List<String>): List<String> {
    val filenameRegex = Regex("\\d{4}-\\d{2}\\.wlk")
    val list = mutableListOf<String>()

    for (inputFilePath in inputFilePathList) {
        val file = File(inputFilePath)
        if (!file.exists()) {
            println("$inputFilePath does not exist.")
            continue
        }
        if (Paths.get(inputFilePath).isDirectory()) {
            list.addAll(file.listFiles { f -> f.name.matches(filenameRegex) && f.isFile }!!.map { it.canonicalPath })
        } else if (file.isFile) {
            if (file.name.matches(filenameRegex)) {
                list.add(file.canonicalPath)
            } else {
                println("$inputFilePath does not match the name format.")
            }
        }
    }

    return list
}