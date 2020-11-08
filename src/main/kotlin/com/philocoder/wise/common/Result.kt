package com.philocoder.wise.common

import java.lang.RuntimeException

enum class Result {

    win,
    lose,
    incomplete;

    companion object {
        fun from(char: String): Result {
            return when (char) {
                "?" -> incomplete
                "+" -> win
                "-" -> lose
                else -> throw RuntimeException()
            }
        }

        fun combine(r1: Result, r2: Result): Result {
            return if (r1 == lose || r2 == lose) lose
            else if (r1 == incomplete || r2 == incomplete) incomplete
            else win
        }
    }
}
