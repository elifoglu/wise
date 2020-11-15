package com.philocoder.wise

import com.philocoder.wise.bet.Bet
import com.philocoder.wise.common.Filter
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.input.Inputs
import com.philocoder.wise.test_util.Helper.csvFolderPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppKtTest {

    @Test
    fun `test without any filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise(inputs).calculate().output
        assertThat(output).contains(
                """|General stats of pool: 
                |W: 1
                |L: 3
                |I: 0
                |W/(W+L): 0.25
                |avg odd: 1.656
                |avg possibility: 0.795
                |avg quality: 1.307
                |""".trimMargin()
        )
    }

    @Test
    fun `test with bet filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.2 }),
                couponFilters = emptyList()
        )
        val output = Wise(inputs).calculate().output
        assertThat(output).contains(
                """|General stats of pool: 
                |W: 0
                |L: 1
                |I: 0
                |W/(W+L): 0.0
                |avg odd: 1.69
                |avg possibility: 0.79
                |avg quality: 1.335""".trimMargin()
        )
    }

    @Test
    fun `test with coupon filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.6 })
        )
        val output = Wise(inputs).calculate().output
        assertThat(output).contains(
                """|Stats of filtered pool:
                |W: 0
                |L: 2
                |I: 0
                |W/(W+L): 0.0
                |avg odd: 1.817
                |avg possibility: 0.763
                |avg quality: 1.385""".trimMargin()
        )
    }
}