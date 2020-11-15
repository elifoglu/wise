package com.philocoder.wise

import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.input.Inputs

fun main() {
    val inputs = Inputs.receive()
    Wise.of(inputs.copy(couponFilters = emptyList()))
            .printOutput()
            .writeToFile()
    Wise.of(inputs)
            .printOutput()
            .printPoolSortedBy(Coupon::quality, 5)
}