package com.philocoder.wise

import com.philocoder.wise.input.Inputs

fun main() {
    val inputs = Inputs.receive()
    Wise(inputs).calculate().printOutput()
}