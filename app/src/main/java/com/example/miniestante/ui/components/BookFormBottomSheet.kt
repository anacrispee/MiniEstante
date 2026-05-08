package com.example.miniestante.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniestante.R
import com.example.miniestante.data.model.Book
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus
import com.example.miniestante.ui.theme.MiniEstanteTheme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookFormBottomSheet(
    editingBook: Book?,
    title: String,
    author: String,
    startDate: String,
    endDate: String,
    status: BookStatus,
    rating: BookRating,
    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onStartDateChange: (String) -> Unit,
    onEndDateChange: (String) -> Unit,
    onStatusChange: (BookStatus) -> Unit,
    onRatingChange: (BookRating) -> Unit,
    onDismiss: () -> Unit,
    onSave: (Book) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    val isFormValid = title.isNotBlank() && author.isNotBlank()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(
                        if (editingBook == null) R.string.form_title_new_book
                        else R.string.form_title_edit_book
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.action_close),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            FormLabel(stringResource(R.string.form_label_title))
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = {
                    Text(
                        stringResource(R.string.form_placeholder_title),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = formFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel(stringResource(R.string.form_label_author))
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = author,
                onValueChange = onAuthorChange,
                placeholder = {
                    Text(
                        stringResource(R.string.form_placeholder_author),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = formFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DateInputField(
                    label = stringResource(R.string.form_label_start_date),
                    value = startDate,
                    onDateSelected = onStartDateChange,
                    modifier = Modifier.weight(1f)
                )
                DateInputField(
                    label = stringResource(R.string.form_label_end_date),
                    value = endDate,
                    onDateSelected = onEndDateChange,
                    minDate = startDate,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel(stringResource(R.string.form_label_status))
            Spacer(modifier = Modifier.height(6.dp))
            EnumDropdown(
                options = BookStatus.entries,
                selected = status,
                label = { it.label },
                onSelected = onStatusChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel(stringResource(R.string.form_label_rating))
            Spacer(modifier = Modifier.height(6.dp))
            EnumDropdown(
                options = BookRating.entries,
                selected = rating,
                label = { it.label },
                onSelected = onRatingChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = stringResource(R.string.form_button_save),
                enabled = isFormValid,
                onClick = {
                    val book = Book(
                        id = editingBook?.id ?: UUID.randomUUID().toString(),
                        title = title.trim(),
                        author = author.trim(),
                        startDate = startDate.ifBlank { null },
                        endDate = endDate.ifBlank { null },
                        status = status,
                        rating = rating,
                        createdAt = editingBook?.createdAt ?: System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    onSave(book)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun <T> EnumDropdown(
    options: List<T>,
    selected: T,
    label: (T) -> String,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded = true },
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label(selected),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = label(option),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    onSelected(option)
                    expanded = false
                },
                leadingIcon = if (option == selected) ({
                    Text("✓", color = MaterialTheme.colorScheme.primary)
                }) else null
            )
        }
    }
}

@Composable
private fun formFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    cursorColor = MaterialTheme.colorScheme.primary
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun BookFormBottomSheetPreview() {
    MiniEstanteTheme {
        BookFormBottomSheet(
            editingBook = null,
            title = "",
            author = "",
            startDate = "",
            endDate = "",
            status = BookStatus.IN_PROGRESS,
            rating = BookRating.WORTH_VOTE,
            onTitleChange = {},
            onAuthorChange = {},
            onStartDateChange = {},
            onEndDateChange = {},
            onStatusChange = {},
            onRatingChange = {},
            onDismiss = {},
            onSave = {}
        )
    }
}
