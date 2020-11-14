package com.philocoder.wise.input

import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.coupon.CouponFilter
import com.philocoder.wise.coupon.CouponPool
import java.io.File
import java.io.FilenameFilter

data class Inputs(val betDay: String,
                  val folder: String,
                  val betCounts: List<Int>,
                  val filters: List<CouponFilter>) {

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

            val filters = arrayListOf(
                    CouponFilter("minOddFilter") { it.odd >= 1.8 },
                    CouponFilter("maxOddFilter") { it.odd <= 2.0 },
                    CouponFilter("minPossibilityFilter") { it.possibility >= 0.7 },
                    CouponFilter("maxPossibilityFilter") { it.possibility <= 0.8 },
                    CouponFilter("minQualityFilter") { it.quality >= 1.5 },
                    CouponFilter("maxQualityFilter") { it.quality <= 1.5 })

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