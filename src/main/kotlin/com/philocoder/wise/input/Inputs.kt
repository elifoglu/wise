package com.philocoder.wise.input

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.philocoder.wise.bet.Bet
import com.philocoder.wise.common.Filter
import com.philocoder.wise.coupon.Coupon
import java.io.File
import java.io.FilenameFilter

data class Inputs(val betDay: String,
                  val folder: String,
                  val betCounts: List<Int>,
                  val betFilters: List<Filter<Bet>>,
                  val couponFilters: List<Filter<Coupon>>,
                  val sorter: Option<Sorter> = None) {

    companion object {
        fun receive(): Inputs {
            val folder = "/home/mert/Desktop/bet/"
            printBetDays(folder)
            val betDay = "28nov20"
            println("Selected bet day: $betDay")

            /*print("Possible bet counts in coupon: ")
            val betCounts = readLine()!!.run {
                if (isNotEmpty()) this.split(',').map { it.toInt() }
                else arrayListOf(2, 3, 4)
            }*/
            val betCounts = arrayListOf(2, 3, 4)

            val betFilters = arrayListOf<Filter<Bet>>(
                    Filter("minPossibilityFilter") { it.possibility >= 0.8 }
            )

            val couponFilters = arrayListOf<Filter<Coupon>>(
                    Filter("minOddFilter") { it.odd >= 2 },
                    //Filter("maxOddFilter") { it.odd <= 2.5 },
                    Filter("minPossibilityFilter") { it.possibility >= 0.5 },
                    //Filter("maxPossibilityFilter") { it.possibility <= 0.8 },
                    //Filter("minQualityFilter") { it.quality >= 1.30 },
                    //Filter("maxQualityFilter") { it.quality <= 2.7 },
            )

            val sorter = Sorter(fn = Coupon::possibility, n = 10)

            return Inputs(betDay, folder, betCounts, betFilters, couponFilters, Some(sorter))
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