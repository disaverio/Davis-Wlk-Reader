package dev.disaverio.wlkconverter.utils

import java.io.File

fun readFileLines(pathname: String) =
    File(pathname).readLines()