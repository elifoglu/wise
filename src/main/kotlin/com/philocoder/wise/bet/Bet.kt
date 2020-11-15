package com.philocoder.wise.bet

import com.philocoder.wise.common.Result
import com.philocoder.wise.util.round

data class Bet(val index: Int,
               val game: String,
               val odd: Double,
               val possibility: Double,
               val result: Result) {

    val quality = odd.round(2) * possibility.round(2)

    override fun toString(): String {
        return "Bet(index=$index, odd=$odd, possibility=$possibility, result=$result)"
    }
}