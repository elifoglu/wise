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

    fun printStats(header: String) {
        println(header)
        val couponsWon = coupons.filter { it.result == Result.win }.count()
        val couponsLost = coupons.filter { it.result == Result.lose }.count()
        val couponsIncomplete = coupons.filter { it.result == Result.incomplete }.count()
        println("W: $couponsWon")
        println("L: $couponsLost")
        println("I: $couponsIncomplete")
        if (couponsWon + couponsLost != 0) {
            println("W/(W+L): ${(couponsWon.toDouble() / (couponsWon + couponsLost)).round(3)}")
        }
        println("avg odd: " + (coupons.sumByDouble { coupon -> coupon.odd } / coupons.count()).round(3))
        println("avg possibility: " + (coupons.sumByDouble { coupon -> coupon.possibility } / coupons.count()).round(3))
        println("avg quality: " + (coupons.sumByDouble { coupon -> coupon.quality } / coupons.count()).round(3))
    }
}