package com.philocoder.wise.util

import java.math.RoundingMode

fun Double.round(scale: Int): Double =
        this.toBigDecimal().setScale(scale, RoundingMode.HALF_EVEN).toDouble()