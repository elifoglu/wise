package com.philocoder.wise

import com.philocoder.wise.bet.BetCombination
import com.philocoder.wise.bet.BetCombinator
import com.philocoder.wise.bet.BetList
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.coupon.CouponPool
import com.philocoder.wise.input.Inputs
import com.philocoder.wise.util.newLine

class Wise(val inputs: Inputs) {

    var output: String = ""

    fun calculate(): Wise {
        val betList = BetList.readFromCSV(inputs.betDay, inputs.folder).filter(inputs.betFilters)
        val combinations = BetCombinator.combine(betList.bets, inputs.betCounts)
        val coupons = combinations.map { combination: BetCombination -> Coupon.from(combination) }
        CouponPool(coupons).apply {
            writeToFile(inputs.betDay, inputs.folder)
            output += getStats("General stats of pool:")
        }
        output += newLine
        CouponPool.filtered(coupons, inputs.couponFilters).apply {
            output += getStats("Stats of filtered pool:")
        }
        //.sortedByDescending { it.quality }
        //.also { it.forEach(::println) }
        return this
    }

    fun printOutput(): Wise {
        println(output)
        return this
    }
}