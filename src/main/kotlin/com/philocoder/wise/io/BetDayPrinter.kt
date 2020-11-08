package com.philocoder.wise.io

import java.io.File
import java.io.FilenameFilter

object BetDayPrinter {

    fun print(folder: String) {
        val file = File(folder)
        val filter = FilenameFilter { _: File, name: String -> name.endsWith(".csv") }
        val csvList = file.list(filter).map { it.substring(0, it.length - 4) }
        csvList.forEachIndexed { i, it -> println("${i + 1}) $it") }
        println()
    }
}