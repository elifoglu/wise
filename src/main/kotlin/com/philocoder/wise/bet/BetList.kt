package com.philocoder.wise.bet

import com.philocoder.wise.common.Result
import java.io.BufferedReader
import java.io.FileReader

data class BetList(val bets: List<Bet>) {

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