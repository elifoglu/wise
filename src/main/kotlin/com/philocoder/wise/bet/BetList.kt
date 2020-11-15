package com.philocoder.wise.bet

import com.philocoder.wise.common.Filter
import com.philocoder.wise.common.Result
import com.philocoder.wise.util.round
import java.io.BufferedReader
import java.io.FileReader

data class BetList(val bets: List<Bet>) {

    fun filter(filters: List<Filter<Bet>>): BetList {
        return BetList(filters.foldRight(bets, { filter: Filter<Bet>, bets: List<Bet> -> bets.filter(filter.fn) }))
    }

    fun getStats(header: String): String {
        val betsWon = bets.filter { it.result == Result.win }.count()
        val betsWonAndLost = bets.filter { it.result == Result.lose }.count() + betsWon
        val betsIncomplete = bets.filter { it.result == Result.incomplete }.count()
        val winRatio = if (betsWonAndLost != 0) (betsWon.toDouble() / betsWonAndLost).round(2).toString() else "?"
        val avgOdd = (bets.sumByDouble { bet -> bet.odd } / bets.count()).round(2)
        val avgPossibility = (bets.sumByDouble { bet -> bet.possibility } / bets.count()).round(2)
        val avgQuality = (bets.sumByDouble { bet -> bet.quality } / bets.count()).round(2)
        return "$header $winRatio ${betsWon}/$betsWonAndLost avg[odd${avgOdd} poss${avgPossibility} qua${avgQuality}] ?${betsIncomplete}"
    }

    companion object {
        fun readFromCSV(betDay: String, folder: String): BetList {
            val path = "$folder$betDay.csv"
            val reader = BufferedReader(FileReader(path))
            val bets = arrayListOf<Bet>()
            var line: String
            while (reader.readLine().also { line = it.trim() } != null) {
                if (line.contains("index")) {
                    continue
                }
                if (line.contains(",,,,,")) {
                    break
                }
                if (line.isNotBlank()) {
                    val row: List<String> = line.split(",")
                    val index = row[0].toInt()
                    val game = row[1].trim()
                    val possibility = row[3].toDouble() / 100
                    val odd = row[4].toDouble()
                    val result = row[5]
                    val bet = Bet(index, game, odd, possibility, Result.from(result))
                    bets.add(bet)
                }
            }
            reader.close()
            return BetList(bets)
        }
    }
}