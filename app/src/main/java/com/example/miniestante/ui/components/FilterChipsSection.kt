package com.example.miniestante.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus
import com.example.miniestante.ui.theme.MiniEstanteTheme

@Composable
fun StatusFilterChips(
    selectedStatus: BookStatus?,
    onStatusSelected: (BookStatus?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppFilterChip(
            label = "Todos",
            selected = selectedStatus == null,
            onClick = { onStatusSelected(null) }
        )
        BookStatus.entries.forEach { status ->
            AppFilterChip(
                label = status.label,
                selected = selectedStatus == status,
                onClick = { onStatusSelected(status) }
            )
        }
    }
}

@Composable
fun RatingFilterChips(
    selectedRating: BookRating?,
    onRatingSelected: (BookRating?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppFilterChip(
            label = "Todas avaliações",
            selected = selectedRating == null,
            onClick = { onRatingSelected(null) }
        )
        BookRating.entries.forEach { rating ->
            AppFilterChip(
                label = rating.label,
                selected = selectedRating == rating,
                onClick = { onRatingSelected(rating) }
            )
        }
    }
}

@Composable
fun AppFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        shape = RoundedCornerShape(50),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outline,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            borderWidth = 1.dp,
            selectedBorderWidth = 0.dp
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun StatusFilterChipsPreview() {
    MiniEstanteTheme {
        StatusFilterChips(selectedStatus = null, onStatusSelected = {})
    }
}
