package com.philocoder.wise.bet

data class BetCombination(val bets: List<Bet>) {

    fun includesRepeatingGames(): Boolean {
        val games = this.bets.map { it.game }
        return games.count() != games.distinct().count()
    }
}