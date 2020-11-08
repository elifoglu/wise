package com.philocoder.wise.bet

import org.paukov.combinatorics3.Generator

object BetCombinator {

    fun combine(bets: List<Bet>, rList: List<Int>): List<BetCombination> {
        return rList.map { r ->
            Generator.combination(bets).simple(r)
                    .map { BetCombination(it) }
                    .filter { combination: BetCombination -> !combination.includesRepeatingGames() }
        }.flatten()
    }
}