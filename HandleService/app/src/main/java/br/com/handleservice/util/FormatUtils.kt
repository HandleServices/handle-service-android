package br.com.handleservice.util

import java.text.NumberFormat
import java.time.LocalTime
import java.util.Locale

object FormatUtils {
    fun formatBRCurrency(value: Number, locale: Locale = Locale("pt", "BR")): String {
        return NumberFormat.getCurrencyInstance(locale).format(value)
    }

    fun LocalTime.formatTime(): String {
        val hours = this.hour
        val minutes = this.minute
        return "${hours}h${if (minutes > 0) "${minutes}m" else ""}"
    }
}