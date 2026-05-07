package com.example.miniestante.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniestante.ui.theme.MiniEstanteTheme
import java.util.Calendar

@Composable
fun DateInputField(
    label: String,
    value: String,           // stored as "yyyy-MM-dd", displayed as "dd/mm/aaaa"
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val displayValue = if (value.isBlank()) "" else value.toDisplayDate()

    OutlinedTextField(
        value = displayValue,
        onValueChange = {},
        readOnly = true,
        label = { Text(label, style = MaterialTheme.typography.bodySmall) },
        placeholder = { Text("dd/mm/aaaa", style = MaterialTheme.typography.bodyMedium) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = "Selecionar data",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                val cal = Calendar.getInstance()
                if (value.isNotBlank()) {
                    val parts = value.split("-")
                    if (parts.size == 3) {
                        cal.set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt())
                    }
                }
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        val formatted = "%04d-%02d-%02d".format(year, month + 1, day)
                        onDateSelected(formatted)
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    )
}

// "yyyy-MM-dd" → "dd/MM/yyyy"
fun String.toDisplayDate(): String {
    return try {
        val parts = this.split("-")
        if (parts.size != 3) return this
        "${parts[2]}/${parts[1]}/${parts[0]}"
    } catch (e: Exception) {
        this
    }
}

@Preview(showBackground = true)
@Composable
private fun DateInputFieldPreview() {
    MiniEstanteTheme {
        DateInputField(label = "Início", value = "2026-05-04", onDateSelected = {})
    }
}
