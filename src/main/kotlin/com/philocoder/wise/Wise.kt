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
        val betList = BetList.readFromCSV(inputs.betDay, inputs.folder) //.also { it.forEach(::println) }
        val combinations = BetCombinator.combine(betList.bets, inputs.betCounts)
        val coupons = combinations.map { combination: BetCombination -> Coupon.from(combination) }
        CouponPool(coupons).apply {
            writeToFile(inputs.betDay, inputs.folder)
            output += getStats("General stats of pool: ")
        }
        output += newLine
        CouponPool.filtered(coupons, inputs.filters).apply {
            output += getStats("Stats of filtered pool:")
        }
        return this
    }

    fun printOutput(): Wise {
        println(output)
        return this
    }
}