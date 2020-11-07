package com.philocoder.wise.input

data class Bet(val index: Int,
               val odd: Double,
               val possibility: Double,
               val result: Result) {

    override fun toString(): String {
        return "Bet(index=$index, odd=$odd, possibility=$possibility)"
    }
}
