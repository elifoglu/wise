package com.philocoder.wise

import com.philocoder.wise.input.Inputs

fun main() {
    val inputs = Inputs.receive()
    Wise.of(inputs.copy(couponFilters = emptyList()))
            .printOutput()
            .writeToFile()
    val withFilters = Wise.of(inputs)
            .printOutput()
    inputs.sorter.fold({}, { withFilters.printPoolSortedBy(it) })
}