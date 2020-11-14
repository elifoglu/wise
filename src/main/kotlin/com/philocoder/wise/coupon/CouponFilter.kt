package com.philocoder.wise.coupon

data class CouponFilter(val name: String, val fn: (Coupon) -> Boolean)
