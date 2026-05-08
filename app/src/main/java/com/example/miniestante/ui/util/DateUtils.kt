package com.example.miniestante.ui.util

private val MONTH_ABBREVIATIONS = listOf(
    "jan", "fev", "mar", "abr", "mai", "jun",
    "jul", "ago", "set", "out", "nov", "dez"
)

fun String.formatDate(): String {
    return try {
        val parts = this.split("-")
        if (parts.size != 3) return this
        val month = parts[1].toInt()
        "${parts[2]} ${MONTH_ABBREVIATIONS[month - 1]} ${parts[0]}"
    } catch (_: Exception) {
        this
    }
}

fun String.toDisplayDate(): String {
    return try {
        val parts = this.split("-")
        if (parts.size != 3) return this
        "${parts[2]}/${parts[1]}/${parts[0]}"
    } catch (_: Exception) {
        this
    }
}
