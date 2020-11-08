package com.philocoder.wise.input

import java.io.File
import java.io.FilenameFilter

data class Inputs(val betDay: String,
                  val folder: String,
                  val betCounts: List<Int>,
                  val filters: Map<String, Double>) {

    companion object {
        fun receive(): Inputs {
            val folder = "/home/mert/Desktop/bet/"

            printBetDays(folder) //30oct20, 2nov20, 7nov20
            val betDay = "2nov20"

            /*print("Possible bet counts in coupon: ")
            val betCounts = readLine()!!.run {
                if (isNotEmpty()) this.split(',').map { it.toInt() }
                else arrayListOf(2, 3, 4)
            }*/
            val betCounts = arrayListOf(2,3,4)

            val filters = mapOf("minOddFilter" to 2.0,
                    "maxOddFilter" to 1.8,
                    "minPossibilityFilter" to 0.7,
                    "maxPossibilityFilter" to 0.80,
                    "minQualityFilter" to 1.5,
                    "maxQualityFilter" to 1.5)

            return Inputs(betDay, folder, betCounts, filters)
        }

        private fun printBetDays(folder: String) {
            val file = File(folder)
            val filter = FilenameFilter { _: File, name: String -> name.endsWith(".csv") }
            val csvList = file.list(filter).map { it.substring(0, it.length - 4) }
            csvList.forEachIndexed { i, it -> println("${i + 1}) $it") }
            println()
        }
    }
}