package com.philocoder.wise

import com.philocoder.wise.bet.BetCombination
import com.philocoder.wise.bet.BetCombinator
import com.philocoder.wise.bet.BetList
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.coupon.CouponPool
import com.philocoder.wise.input.Inputs
import com.philocoder.wise.input.Sorter

class Wise(private val inputs: Inputs, betList: BetList, private val couponPool: CouponPool) {

    val output = """
        |${betList.getStats("Stats of bets:")}
        |${couponPool.getStats(if (inputs.couponFilters.isEmpty()) "Stats of pool:" else "Stats of filtered pool:")}
        """.trimMargin()

    fun printOutput() = apply { println(output) }
    fun writeToFile() = apply { couponPool.writeToFile(inputs.betDay, inputs.folder) }
    fun printPoolSortedBy(sorter: Sorter) = couponPool.coupons
            .sortedByDescending(sorter.fn)
            .take(sorter.n)
            .forEach(::println)

    companion object {
        fun of(inputs: Inputs): Wise {
            val betList = BetList.readFromCSV(inputs.betDay, inputs.folder).filter(inputs.betFilters)
            val combinations = BetCombinator.combine(betList.bets, inputs.betCounts)
            val coupons = combinations.map { combination: BetCombination -> Coupon.from(combination) }
            val couponPool = CouponPool.filtered(coupons, inputs.couponFilters)
            return Wise(inputs, betList, couponPool)
        }
    }
}