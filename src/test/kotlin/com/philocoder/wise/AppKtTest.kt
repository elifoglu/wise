package com.philocoder.wise

import com.philocoder.wise.input.Inputs
import com.philocoder.wise.test_util.Helper.csvFolderPath
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppKtTest {

    @Test
    fun `test`() {
        val inputs = Inputs(betDay = "dayA", folder = csvFolderPath, betCounts = listOf(2, 3, 4), filters = mapOf())
        val output = Wise(inputs).calculate().output
        assertThat(output).isEqualTo(
                """|General stats of pool: 
                |W: 1
                |L: 3
                |I: 0
                |W/(W+L): 0.25
                |avg odd: 1.656
                |avg possibility: 0.795
                |avg quality = 1.307
                |
                |Stats of filtered pool:
                |W: 1
                |L: 3
                |I: 0
                |W/(W+L): 0.25
                |avg odd: 1.656
                |avg possibility: 0.795
                |avg quality = 1.307
                |""".trimMargin()
        )
    }
}