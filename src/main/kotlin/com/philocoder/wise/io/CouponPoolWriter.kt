package com.philocoder.wise.io

import com.philocoder.wise.coupon.Coupon
import java.io.File
import java.io.FileWriter

object CouponPoolWriter {

    fun write(couponPool: List<Coupon>, betDay: String, folder: String)  {
        couponPool.sortedByDescending { it.quality }.joinToString(separator = "\n", transform = Coupon::toString)
                .run {
                    val writer = FileWriter(File("$folder${betDay}output"))
                    writer.write(this)
                    writer.flush()
                    writer.close()
                }
    }
}