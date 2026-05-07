package com.example.miniestante.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniestante.data.model.BookStatus
import com.example.miniestante.ui.theme.MiniEstanteTheme
import com.example.miniestante.ui.theme.StatusInProgressBg
import com.example.miniestante.ui.theme.StatusInProgressText
import com.example.miniestante.ui.theme.StatusNotFinishedBg
import com.example.miniestante.ui.theme.StatusNotFinishedText
import com.example.miniestante.ui.theme.StatusReadBg
import com.example.miniestante.ui.theme.StatusReadText
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme

@Composable
fun StatusBadge(status: BookStatus, modifier: Modifier = Modifier) {
    val (bg, textColor) = when (status) {
        BookStatus.IN_PROGRESS -> StatusInProgressBg to StatusInProgressText
        BookStatus.READ -> StatusReadBg to StatusReadText
        BookStatus.NOT_FINISHED -> StatusNotFinishedBg to StatusNotFinishedText
    }
    Text(
        text = status.label,
        style = MaterialTheme.typography.labelMedium,
        color = textColor,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun StatusBadgePreview() {
    MiniEstanteTheme {
        StatusBadge(BookStatus.IN_PROGRESS)
        StatusBadge(BookStatus.READ)
        StatusBadge(BookStatus.NOT_FINISHED)
    }
}
