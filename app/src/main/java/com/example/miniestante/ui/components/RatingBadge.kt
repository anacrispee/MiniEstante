package com.example.miniestante.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.ui.theme.MiniEstanteTheme
import com.example.miniestante.ui.theme.RatingBlacklistBg
import com.example.miniestante.ui.theme.RatingBlacklistText
import com.example.miniestante.ui.theme.RatingNoneBg
import com.example.miniestante.ui.theme.RatingNoneText
import com.example.miniestante.ui.theme.RatingWorthBg
import com.example.miniestante.ui.theme.RatingWorthText

@Composable
fun RatingBadge(rating: BookRating, modifier: Modifier = Modifier) {
    val (bg, textColor) = when (rating) {
        BookRating.WORTH_VOTE -> RatingWorthBg to RatingWorthText
        BookRating.BLACKLIST -> RatingBlacklistBg to RatingBlacklistText
        BookRating.NONE -> RatingNoneBg to RatingNoneText
    }
    Text(
        text = rating.label,
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
private fun RatingBadgePreview() {
    MiniEstanteTheme {
        RatingBadge(BookRating.WORTH_VOTE)
    }
}
