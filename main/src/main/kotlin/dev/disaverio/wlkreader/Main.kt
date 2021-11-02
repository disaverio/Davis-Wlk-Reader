package dev.disaverio.wlkreader

import dev.disaverio.wlkreader.service.Service
import dev.disaverio.wlkreader.service.output.OutputFormat
import kotlinx.cli.*
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.isDirectory

fun main(args: Array<String>) {
    val parser = ArgParser("WlkConverter")
    val inputFilePathList by parser.option(ArgType.String, shortName = "i", description = "Input file path").required().multiple()
    var outputFilePath by parser.option(ArgType.String, shortName = "o", description = "Output file path")
    val outputFormat by parser.option(ArgType.Choice<OutputFormat>(), shortName = "f", description = "Format for output file").default(OutputFormat.CSV) //.multiple()
    val outputFieldsListFilePath by parser.option(ArgType.String, shortName = "p", description = "File containing list of fields printed in output")

    parser.parse(args)

    val fileList = getSanitizedFileList(inputFilePathList)
    val service = Service(outputFilePath, outputFormat, outputFieldsListFilePath)

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