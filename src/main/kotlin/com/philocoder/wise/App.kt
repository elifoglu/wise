package com.philocoder.wise

import com.philocoder.wise.bet.BetCombination
import com.philocoder.wise.bet.BetCombinator
import com.philocoder.wise.bet.BetList
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.coupon.CouponPool
import com.philocoder.wise.input.Inputs

fun main() {
    val inputs = Inputs.receive()

    val betList = BetList.readFromCSV(inputs.betDay, inputs.folder) //.also { it.forEach(::println) }
    val combinations = BetCombinator.combine(betList.bets, inputs.betCounts)
    val coupons = combinations.map { combination: BetCombination -> Coupon.from(combination) }
    val couponPool = CouponPool(coupons).apply {
        writeToFile(inputs.betDay, inputs.folder)
        printStats("General stats of pool: ")
        println()
    }

    val filters = inputs.filters
    couponPool.coupons
            .filter { it.odd >= filters["minOddFilter"]!! }
            //.filter { it.odd <= filters["maxOddFilter"]!! }
            .filter { it.possibility >= filters["minPossibilityFilter"]!! }
            //.filter { it.possibility <= filters["maxPossibilityFilter"]!! }
            //.filter { it.quality >= filters["minQualityFilter"]!! }
            //.filter { it.quality <= filters["maxQualityFilter"]!! }
            .sortedByDescending { it.quality }
            //.also { it.forEach(::println) }
            .also { CouponPool(it).printStats("Stats of filtered pool:") }
}