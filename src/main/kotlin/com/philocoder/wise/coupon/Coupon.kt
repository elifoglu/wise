package com.philocoder.wise.coupon

import com.philocoder.wise.BetCombination
import com.philocoder.wise.Result
import com.philocoder.wise.bet.Bet
import com.philocoder.wise.util.round

data class Coupon(val bets: List<Bet>,
                  val odd: Double,
                  val possibility: Double,
                  val result: Result) {

    val quality = odd.round(2) * possibility.round(2)

    override fun toString(): String {
        val betIndexes = bets.map { it.index }
        return "bets=$betIndexes, odd=${odd.round(3)}, possibility=${possibility.round(3)}, quality=${quality.round(2)}, result=$result"
    }

    companion object {
        fun from(combination: BetCombination): Coupon {
            val reduced: Bet = combination.reduce { b1, b2 -> Bet(1, "", b1.odd * b2.odd, b1.possibility * b2.possibility, Result.combine(b1.result, b2.result)) }
            return Coupon(combination, reduced.odd, reduced.possibility, reduced.result)
        }
    }
}