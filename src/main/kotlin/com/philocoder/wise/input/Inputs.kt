package com.philocoder.martingalish.input

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.philocoder.martingalish.bet.BetResult.GainMoney
import com.philocoder.martingalish.bet.BetResult.LoseMoney

data class Inputs(val strategyInput: String,
                  val odd: Double,
                  val gainRatios: List<Double>,
                  val lossRatio: Option<Double>,
                  val actualBankroll: Option<Double>) {

    companion object {
        fun receive(): Inputs {
            var strategyInput: String
            do {
                print("Enter your martingalish strategy (e.g: gggb or ggbl): ")
                strategyInput = readLine()!!
            } while (!InputValidator.isValidStrategyInput(strategyInput))

            var odd: Double
            do {
                print("Enter odd: ")
                odd = readLine()!!.toDouble()
            } while (!InputValidator.isValidOdd(odd))

            val gainRatios = arrayListOf<Double>()
            val gainMoneyRepresentations = strategyInput.toCharArray().filter { it == GainMoney.representation }
            if (gainMoneyRepresentations.size > 1) {
                println("Enter gain ratio for each '${GainMoney.representation}'...")
                gainMoneyRepresentations.drop(1)
                        .forEachIndexed { i, _ ->
                            print("Enter for ${i + 2}. '${GainMoney.representation}': ")
                            gainRatios.add(readLine()!!.toDouble())
                        }
            }

            val lossRatio = if (strategyInput.contains(LoseMoney.representation)) {
                print("Enter loss ratio: ")
                Some(readLine()!!.toDouble())
            } else None

            print("Enter actual bankroll (press enter to skip): ")
            val actualBankroll = readLine()!!.let {
                if (it.isNotEmpty()) Some(it.toDouble()) else None
            }

            return Inputs(
                    strategyInput = strategyInput,
                    odd = odd,
                    gainRatios = gainRatios,
                    lossRatio = lossRatio,
                    actualBankroll = actualBankroll)
        }
    }
}