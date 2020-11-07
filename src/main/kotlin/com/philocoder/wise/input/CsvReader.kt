package com.philocoder.wise.input

import com.philocoder.wise.input.Result.WIN
import java.io.BufferedReader
import java.io.FileReader

object CsvReader {

    fun readBetList(filename: String): List<Bet> {
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
                val possibility = row[3].toDouble() / 100
                val odd = row[4].toDouble()
                val result = row[5]
                val bet = Bet(index, odd, possibility, Result.from(result))
                bets.add(bet)
            }
        }
        reader.close()
        return bets
    }
}