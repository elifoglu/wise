package com.philocoder.wise

import com.philocoder.wise.input.Bet
import com.philocoder.wise.input.Coupon
import com.philocoder.wise.input.CsvReader
import com.philocoder.wise.input.Result
import com.philocoder.wise.input.Result.*
import org.paukov.combinatorics3.Generator


fun main() {
    val filename = "/home/mert/Desktop/bahis/nov2.csv"
    val bets = CsvReader.readBetList(filename).also { it.forEach(::println) }


    print("Possible bet counts in coupon: ")
    val betCounts: List<Int> = readLine()!!.split(',').map { it.toInt() }
    val couponPool = arrayListOf<Coupon>()
    betCounts.forEach { betCount ->
        Generator.combination(bets.map { it.index }).simple(betCount).forEach { combination: MutableList<Int> ->
            val members: List<Bet> = combination.map { betIndex -> bets.first { it.index == betIndex } }
            val reduced: Bet = members.reduce { b1, b2 -> Bet(1, b1.odd * b2.odd, b1.possibility * b2.possibility, Result.reduce(b1.result, b2.result)) }
            val coupon = Coupon(members, reduced.odd, reduced.possibility, reduced.result)
            couponPool.add(coupon)
        }
    }


    val oddThresholdToPrintCoupon = 2

    couponPool.sortedByDescending { it.quality }
            .filter { it.odd > oddThresholdToPrintCoupon }
            .forEach {
                println(it.toString())
            }

    val qualityThresholdToPrintCoupon = 1.5
    val possibilityThresholdToPrintCoupon = 0.7
    val filteredCouponPool = couponPool
            .filter { it.possibility > possibilityThresholdToPrintCoupon }
            .filter { it.odd > oddThresholdToPrintCoupon }
            .also { println("Average odd: " + it.sumByDouble { coupon -> coupon.odd } / it.count()) }
            .also { println("Average possibility: " + it.sumByDouble { coupon -> coupon.possibility } / it.count()) }
            .also { println("Average quality: " + it.sumByDouble { coupon -> coupon.quality } / it.count()) }

    val couponsWon = filteredCouponPool.filter { it.result == WIN }.count()
    val couponsLost = filteredCouponPool.filter { it.result == LOSE }.count()
    val couponsIncomplete = filteredCouponPool.filter { it.result == INCOMPLETE }.count()
    println("W: $couponsWon")
    println("L: $couponsLost")
    println("I: $couponsIncomplete")
    println("W/(W+L): ${couponsWon.toDouble() / (couponsWon + couponsLost)}")
}