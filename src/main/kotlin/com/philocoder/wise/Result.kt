package com.philocoder.wise.input

import java.lang.RuntimeException

enum class Result {

    INCOMPLETE,
    WIN,
    LOSE;

    companion object {
        fun reduce(r1: Result, r2: Result): Result {
            return if (r1 == LOSE || r2 == LOSE) LOSE
            else if (r1 == INCOMPLETE || r2 == INCOMPLETE) INCOMPLETE
            else WIN
        }

        fun from(char: String): Result {
            return when (char) {
                "?" -> INCOMPLETE
                "+" -> WIN
                "-" -> LOSE
                else -> throw RuntimeException()
            }
        }
    }
}
