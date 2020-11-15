package com.philocoder.wise.input

import com.philocoder.wise.bet.Bet
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.common.Filter
import java.io.File
import java.io.FilenameFilter

data class Inputs(val betDay: String,
                  val folder: String,
                  val betCounts: List<Int>,
                  val betFilters: List<Filter<Bet>>,
                  val couponFilters: List<Filter<Coupon>>) {

    companion object {
        fun receive(): Inputs {
            val folder = "/home/mert/Desktop/bet/"
            printBetDays(folder) //30oct20, 2nov20, 7nov20
            val betDay = "7nov20"
            println("Selected bet day: $betDay")
            println()

            /*print("Possible bet counts in coupon: ")
            val betCounts = readLine()!!.run {
                if (isNotEmpty()) this.split(',').map { it.toInt() }
                else arrayListOf(2, 3, 4)
            }*/
            val betCounts = arrayListOf(2, 3, 4)

            val betFilters = arrayListOf<Filter<Bet>>(
                    Filter("minPossibilityFilter") { it.possibility >= 0.85 }
            )

            val couponFilters = arrayListOf<Filter<Coupon>>(
                    Filter("minOddFilter") { it.odd >= 1.8 },
                    //Filter("maxOddFilter") { it.odd <= 2.0 },
                    Filter("minPossibilityFilter") { it.possibility >= 0.7 },
                    //Filter("maxPossibilityFilter") { it.possibility <= 0.8 },
                    //Filter("minQualityFilter") { it.quality >= 0.5 },
                    //Filter("maxQualityFilter") { it.quality <= 2.7 },
            )

            return Inputs(betDay, folder, betCounts, betFilters, couponFilters)
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