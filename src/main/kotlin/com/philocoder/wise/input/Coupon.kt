package com.philocoder.wise.input

import com.philocoder.wise.util.round

data class Coupon(val bets: List<Bet>,
                  val odd: Double,
                  val possibility: Double,
                  val result: Result) {

    val quality = odd.round(2) * possibility.round(2)

    override fun toString(): String {
        val betIndexes = bets.map { it.index }
        return "bets=$betIndexes, odd=${odd.round(2)}, possibility=${possibility.round(2)}, quality=${quality.round(2)}"
    }
}
