package dev.disaverio.wlkreader.utils

import java.io.File

fun readFileLines(pathname: String) =
    File(pathname).readLines()