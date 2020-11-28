package com.philocoder.wise.input

import com.philocoder.wise.coupon.Coupon

data class Sorter(val fn: (Coupon) -> Double,
                  val n: Int)