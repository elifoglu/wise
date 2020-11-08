package com.philocoder.wise

import com.philocoder.wise.bet.Bet
import org.paukov.combinatorics3.Generator

typealias BetCombination = MutableList<Bet>

object BetCombinator {

    fun generate(bets: List<Bet>, rList: List<Int>): List<MutableList<Bet>> {
        return rList.map { r ->
            Generator.combination(bets).simple(r)
                    .filter { combination: BetCombination -> !combination.includesRepeatingGames() }
        }.flatten()
    }

    private fun BetCombination.includesRepeatingGames(): Boolean {
        val games = this.map { it.game }
        return games.count() != games.distinct().count()
    }
}