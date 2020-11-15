package com.philocoder.wise

import com.philocoder.wise.common.Filter
import com.philocoder.wise.input.Inputs
import com.philocoder.wise.test_util.Helper.csvFolderPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppKtTest {

    @Test
    fun `it should build bet list stats output correctly without any filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        assertThat(output).contains("Stats of bets: 0.67 2/3 avg[odd1.25 poss0.9 qua1.13] ?0")
    }

    @Test
    fun `it should build bet list stats output correctly with bet filtering`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.2 }),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        assertThat(output).contains("Stats of bets: 0.5 1/2 avg[odd1.3 poss0.89 qua1.16] ?0")
    }

    @Test
    fun `it should build coupon pool stats output correctly without any filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        assertThat(output).contains("Stats of pool: 0.25 1/4 avg[odd1.66 poss0.8 qua1.31] ?0")
    }

    @Test
    fun `it should build coupon pool stats output correctly with bet filtering`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.2 }),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        assertThat(output).contains("Stats of pool: 0.0 0/1 avg[odd1.69 poss0.79 qua1.34] ?0")
    }

    @Test
    fun `it should filter coupons`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.6 })
        )
        val output = Wise.of(inputs).output
        assertThat(output).contains("Stats of filtered pool: 0.0 0/2 avg[odd1.82 poss0.76 qua1.39] ?0")
    }

    @Test
    fun `it should not create coupons with duplicate games`() {
        val inputs = Inputs(
                betDay = "dayB",
                folder = csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        assertThat(output).contains("Stats of pool: 1.0 2/2 avg[odd1.75 poss0.74 qua1.29] ?0")
    }
}