package com.philocoder.martingalish.input

import com.philocoder.martingalish.bet.BetResult.*

object InputValidator {

    private val allValidChars = arrayOf(
            GainMoney.representation,
            LoseMoney.representation,
            BackToBankroll.representation
    )

    fun isValidStrategyInput(input: String): Boolean {
        if (input.toCharArray().any { !allValidChars.contains(it) }) {
            println("Your strategy contains invalid chars. Valid chars are: ${allValidChars.joinToString(", ")}")
            return false
        }
        if (!input.startsWith(GainMoney.representation)) {
            println("Your strategy should start with a ${GainMoney.representation}")
            return false
        }
        return true
    }

    fun isValidOdd(odd: Double): Boolean {
        if (odd <= 1.0) {
            println("Don't you want to gain some money?")
            return false
        }
        return true
    }
}