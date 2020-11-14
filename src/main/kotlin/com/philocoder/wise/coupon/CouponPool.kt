package com.philocoder.wise.coupon

import com.philocoder.wise.common.Result
import com.philocoder.wise.util.round
import java.io.File
import java.io.FileWriter

data class CouponPool(val coupons: List<Coupon>) {

    fun writeToFile(betDay: String, folder: String) {
        coupons.sortedByDescending { it.quality }.joinToString(separator = "\n", transform = Coupon::toString)
                .run {
                    val writer = FileWriter(File("$folder${betDay}output"))
                    writer.write(this)
                    writer.flush()
                    writer.close()
                }
    }

    fun getStats(header: String): String {
        val couponsWon = coupons.filter { it.result == Result.win }.count()
        val couponsLost = coupons.filter { it.result == Result.lose }.count()
        val couponsIncomplete = coupons.filter { it.result == Result.incomplete }.count()
        val winRatio = if (couponsWon + couponsLost != 0) {
            (couponsWon.toDouble() / (couponsWon + couponsLost)).round(3).toString()
        } else "?"
        val avgOdd = (coupons.sumByDouble { coupon -> coupon.odd } / coupons.count()).round(3)
        val avgPossibility = (coupons.sumByDouble { coupon -> coupon.possibility } / coupons.count()).round(3)
        val avgQuality = (coupons.sumByDouble { coupon -> coupon.quality } / coupons.count()).round(3)
        return """$header
        |W: $couponsWon
        |L: $couponsLost
        |I: $couponsIncomplete
        |W/(W+L): $winRatio
        |avg odd: $avgOdd
        |avg possibility: $avgPossibility
        |avg quality = $avgQuality
        |""".trimMargin()
    }

    companion object {
        fun filtered(coupons: List<Coupon>, filters: Map<String, Double>) = CouponPool(coupons)
                //.filter { it.odd >= filters["minOddFilter"]!! }
                //.filter { it.odd <= filters["maxOddFilter"]!! }
                //.filter { it.possibility >= filters["minPossibilityFilter"]!! }
                //.filter { it.possibility <= filters["maxPossibilityFilter"]!! }
                //.filter { it.quality >= filters["minQualityFilter"]!! }
                //.filter { it.quality <= filters["maxQualityFilter"]!! })
        //.sortedByDescending { it.quality }
        //.also { it.forEach(::println) }
    }
}