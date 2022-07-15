package dev.disaverio.wlkconverter

import dev.disaverio.wlkconverter.service.Service
import dev.disaverio.wlkconverter.service.output.OutputFormat
import dev.disaverio.wlkreader.types.units.UnitSystem
import kotlinx.cli.*
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.isDirectory

fun main(args: Array<String>) {
    val parser = ArgParser("WlkConverter")
    val version by parser.option(ArgType.Boolean, "version", "v", "Print version")
    val inputFilePathList by parser.option(ArgType.String, "input", "i", "Input file path").multiple()
    val outputFolderPath by parser.option(ArgType.String, "output", "o", "Output folder path")
    val unitSystem by parser.option(ArgType.Choice<UnitSystem>(), "unit", "u", "Unit system used for values in the printed output")
    val skipHeader by parser.option(ArgType.Boolean, "skip-header", "s", "Skip header printing").default(false)
    val outputFormat by parser.option(ArgType.Choice<OutputFormat>(), "outputFormat", "f", "Format for output file").default(OutputFormat.CSV) //.multiple()
    val outputFieldsListFilePath by parser.option(ArgType.String, "fieldsListFile", "p", "Path to file containing list of fields printed in output")

    parser.parse(args)

    if (version == true) {
        val properties = Properties()
        properties.load(object {}.javaClass.classLoader.getResourceAsStream("application.properties"))
        println(properties.getProperty("version"))
        return
    }

    val fileList = getSanitizedFileList(inputFilePathList as MutableList<String>)
    val checkedOutputFolderPath = getOutputFolderPath(outputFolderPath)
    val service = Service(unitSystem, outputFieldsListFilePath, skipHeader, checkedOutputFolderPath, outputFormat)

    println("Valid files found:")
    fileList.forEach { println("  $it") }

    fileList.forEach { service.printMonth(it) }
}

private fun getSanitizedFileList(inputFilePathList: MutableList<String>): List<String> {
    if (inputFilePathList.isEmpty()) inputFilePathList.add(Paths.get("").toAbsolutePath().toString())

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

private fun getOutputFolderPath(outputFolder: String?): String {
    if (outputFolder == null) {
        return Paths.get("").toAbsolutePath().toString()
    }
    if (!Paths.get(outputFolder).isDirectory()) {
        println("Specified output folder is not a folder. Current folder will be used to save output files.")
        return Paths.get("").toAbsolutePath().toString()
    }

    return Paths.get(outputFolder).toAbsolutePath().toString()
}
