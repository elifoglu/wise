package com.philocoder.wise

import com.philocoder.wise.Result.*
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.io.BetDayPrinter
import com.philocoder.wise.io.BetListReader
import com.philocoder.wise.io.CouponPoolWriter

fun main() {

    val folder = "/home/mert/Desktop/bet/"
    BetDayPrinter.print(folder)

    val betDay = "30oct20"
    //betDay = "2nov20"
    //betDay = "7nov20"

    val filename = "$folder$betDay.csv"
    val bets = BetListReader.readFromCSV(filename) //.also { it.forEach(::println) }

    print("Possible bet counts in coupon: ")
    val betCounts = readLine()!!.run {
        if (isNotEmpty()) this.split(',').map { it.toInt() }
        else arrayListOf(2, 3, 4)
    }

    val combinations: List<BetCombination> = BetCombinator.generate(bets, betCounts)
    val couponPool: List<Coupon> = combinations.map { combination: BetCombination -> Coupon.from(combination) }
    CouponPoolWriter.write(couponPool, betDay, folder)

    val minOddFilter = 2
    val maxOddFilter = 1.8
    val minPossibilityFilter = 0.74
    val maxPossibilityFilter = 0.80
    val minQualityFilter = 1.5
    val maxQualityFilter = 1.5

    val filteredCouponPool = couponPool
            .filter { it.odd >= minOddFilter }
            //.filter { it.odd <= maxOddFilter }
            .filter { it.possibility >= minPossibilityFilter }
            .filter { it.possibility <= maxPossibilityFilter }
            //.filter { it.quality >= minQualityFilter }
            //.filter { it.quality <= maxQualityFilter }
            .sortedByDescending { it.quality }
            .also { it.forEach(::println) }
            .also { println("Average odd: " + it.sumByDouble { coupon -> coupon.odd } / it.count()) }
            .also { println("Average possibility: " + it.sumByDouble { coupon -> coupon.possibility } / it.count()) }
            .also { println("Average quality: " + it.sumByDouble { coupon -> coupon.quality } / it.count()) }

    val couponsWon = filteredCouponPool.filter { it.result == win }.count()
    val couponsLost = filteredCouponPool.filter { it.result == lose }.count()
    val couponsIncomplete = filteredCouponPool.filter { it.result == incomplete }.count()
    println("W: $couponsWon")
    println("L: $couponsLost")
    println("I: $couponsIncomplete")
    if (couponsWon + couponsLost != 0) {
        println("W/(W+L): ${couponsWon.toDouble() / (couponsWon + couponsLost)}")
    }
}