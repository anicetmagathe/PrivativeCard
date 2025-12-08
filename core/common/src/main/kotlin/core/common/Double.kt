package core.common

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun Double.format(): String {
    return decimalFormat.format(this)
}

private val decimalFormat = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale.FRANCE))