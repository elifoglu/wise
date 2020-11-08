package com.philocoder.wise.io

import com.philocoder.wise.Result
import com.philocoder.wise.bet.Bet
import java.io.BufferedReader
import java.io.FileReader

object BetListReader {

    fun readFromCSV(filename: String): List<Bet> {
        val reader = BufferedReader(FileReader(filename))
        var line: String
        val bets = arrayListOf<Bet>()
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
        return bets
    }
}