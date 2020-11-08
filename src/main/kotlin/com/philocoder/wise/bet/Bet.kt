package com.philocoder.wise.bet

import com.philocoder.wise.common.Result

data class Bet(val index: Int,
               val game: String,
               val odd: Double,
               val possibility: Double,
               val result: Result) {

    override fun toString(): String {
        return "Bet(index=$index, odd=$odd, possibility=$possibility, result=$result)"
    }
}