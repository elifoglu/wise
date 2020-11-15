package com.philocoder.wise.common

data class Filter<T>(val name: String, val fn: (T) -> Boolean)
