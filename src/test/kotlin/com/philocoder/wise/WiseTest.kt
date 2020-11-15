package com.philocoder.wise

import com.philocoder.wise.common.Filter
import com.philocoder.wise.coupon.Coupon
import com.philocoder.wise.input.Inputs
import com.philocoder.wise.test_util.Helper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class WiseTest {

    @Test
    fun `it should build bet list stats output correctly without any filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        Assertions.assertThat(output).contains("Stats of bets: 0.67 2/3 avg[odd1.25 poss0.9 qua1.13] ?0")
    }

    @Test
    fun `it should build bet list stats output correctly with bet filtering`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.2 }),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        Assertions.assertThat(output).contains("Stats of bets: 0.5 1/2 avg[odd1.3 poss0.89 qua1.16] ?0")
    }

    @Test
    fun `it should build coupon pool stats output correctly without any filters`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        Assertions.assertThat(output).contains("Stats of pool: 0.25 1/4 avg[odd1.66 poss0.8 qua1.31] ?0")
    }

    @Test
    fun `it should build coupon pool stats output correctly with bet filtering`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.2 }),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        Assertions.assertThat(output).contains("Stats of pool: 0.0 0/1 avg[odd1.69 poss0.79 qua1.34] ?0")
    }

    @Test
    fun `it should filter coupons`() {
        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = arrayListOf(Filter("minOddFilter") { it.odd > 1.6 })
        )
        val output = Wise.of(inputs).output
        Assertions.assertThat(output).contains("Stats of filtered pool: 0.0 0/2 avg[odd1.82 poss0.76 qua1.39] ?0")
    }

    @Test
    fun `it should not create coupons with duplicate games`() {
        val inputs = Inputs(
                betDay = "dayB",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        val output = Wise.of(inputs).output
        Assertions.assertThat(output).contains("Stats of pool: 1.0 2/2 avg[odd1.75 poss0.74 qua1.29] ?0")
    }

    @Test
    fun `it should sort coupon pool by odd`() {
        val standartOut = System.out
        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        Wise.of(inputs).printPoolSortedBy(Coupon::odd, 3)
        Assertions.assertThat(outputStreamCaptor.toString()).isEqualTo("""
            |bets=[1, 2, 3], odd=1.944, possibility=0.735, quality=1.44, result=lose
            |bets=[1, 2], odd=1.69, possibility=0.79, quality=1.34, result=lose
            |bets=[1, 3], odd=1.495, possibility=0.79, quality=1.18, result=win
            |""".trimMargin())

        System.setOut(standartOut)
    }

    @Test
    fun `it should sort coupon pool by possibility`() {
        val standartOut = System.out
        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        Wise.of(inputs).printPoolSortedBy(Coupon::possibility, 3)
        Assertions.assertThat(outputStreamCaptor.toString()).isEqualTo("""
            |bets=[2, 3], odd=1.495, possibility=0.865, quality=1.28, result=lose
            |bets=[1, 2], odd=1.69, possibility=0.79, quality=1.34, result=lose
            |bets=[1, 3], odd=1.495, possibility=0.79, quality=1.18, result=win
            |""".trimMargin())

        System.setOut(standartOut)
    }

    @Test
    fun `it should sort coupon pool by quality`() {
        val standartOut = System.out
        val outputStreamCaptor = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        val inputs = Inputs(
                betDay = "dayA",
                folder = Helper.csvFolderPath,
                betCounts = listOf(2, 3, 4),
                betFilters = emptyList(),
                couponFilters = emptyList()
        )
        Wise.of(inputs).printPoolSortedBy(Coupon::quality, 3)
        Assertions.assertThat(outputStreamCaptor.toString()).isEqualTo("""
            |bets=[1, 2, 3], odd=1.944, possibility=0.735, quality=1.44, result=lose
            |bets=[1, 2], odd=1.69, possibility=0.79, quality=1.34, result=lose
            |bets=[2, 3], odd=1.495, possibility=0.865, quality=1.28, result=lose
            |""".trimMargin())

        System.setOut(standartOut)
    }
}