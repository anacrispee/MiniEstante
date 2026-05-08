package com.example.miniestante.ui.books

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniestante.data.model.Book
import com.example.miniestante.ui.components.BackupDialog
import com.example.miniestante.ui.components.BookCard
import com.example.miniestante.ui.components.BookFormBottomSheet
import com.example.miniestante.ui.components.EmptyBooksState
import com.example.miniestante.ui.components.RatingFilterChips
import com.example.miniestante.ui.components.SearchBookField
import com.example.miniestante.ui.components.SortDropdown
import com.example.miniestante.ui.components.StatusFilterChips
import com.example.miniestante.ui.theme.MiniEstanteTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(viewModel: BookListViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var bookToDelete by remember { mutableStateOf<Book?>(null) }

    // File picker for import
    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val jsonString = context.readTextFromUri(it)
            if (jsonString != null) {
                viewModel.onAction(BookListAction.OnImportJsonClicked(jsonString))
            } else {
                scope.launch { snackbarHostState.showSnackbar("Não foi possível ler o arquivo.") }
            }
        }
    }

    // File creator for export
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        uri?.let {
            val jsonString = viewModel.getBooksAsJson()
            if (jsonString.isNotBlank()) {
                context.writeTextToUri(it, jsonString)
                scope.launch { snackbarHostState.showSnackbar("Exportado com sucesso!") }
            }
        }
    }

    // Show snackbar messages from ViewModel
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onAction(BookListAction.OnClearMessages)
        }
    }
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onAction(BookListAction.OnClearMessages)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAction(BookListAction.OnAddBookClicked) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Meus Livros",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val count = uiState.books.size
                    Text(
                        text = "$count ${if (count == 1) "livro" else "livros"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    IconButton(onClick = { viewModel.onAction(BookListAction.OnBackupClicked) }) {
                        Icon(
                            imageVector = Icons.Outlined.Archive,
                            contentDescription = "Backup",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search
            SearchBookField(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onAction(BookListAction.OnSearchChanged(it)) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Status filter chips
            StatusFilterChips(
                selectedStatus = uiState.selectedStatusFilter,
                onStatusSelected = { viewModel.onAction(BookListAction.OnStatusFilterSelected(it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating filter chips + sort dropdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingFilterChips(
                    selectedRating = uiState.selectedRatingFilter,
                    onRatingSelected = { viewModel.onAction(BookListAction.OnRatingFilterSelected(it)) },
                    modifier = Modifier.weight(1f)
                )
                SortDropdown(
                    selectedSort = uiState.selectedSortOption,
                    onSortSelected = { viewModel.onAction(BookListAction.OnSortSelected(it)) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Book list or empty state
            if (uiState.filteredBooks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyBooksState()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp, top = 4.dp)
                ) {
                    items(uiState.filteredBooks, key = { it.id }) { book ->
                        BookCard(
                            book = book,
                            onEditClick = { viewModel.onAction(BookListAction.OnEditBookClicked(book)) },
                            onDeleteClick = { bookToDelete = book }
                        )
                    }
                }
            }
        }
    }

    // Book form bottom sheet
    if (uiState.isBookFormVisible) {
        BookFormBottomSheet(
            editingBook = uiState.editingBook,
            title = uiState.formTitle,
            author = uiState.formAuthor,
            startDate = uiState.formStartDate,
            endDate = uiState.formEndDate,
            status = uiState.formStatus,
            rating = uiState.formRating,
            onTitleChange = { viewModel.onAction(BookListAction.OnFormTitleChanged(it)) },
            onAuthorChange = { viewModel.onAction(BookListAction.OnFormAuthorChanged(it)) },
            onStartDateChange = { viewModel.onAction(BookListAction.OnFormStartDateChanged(it)) },
            onEndDateChange = { viewModel.onAction(BookListAction.OnFormEndDateChanged(it)) },
            onStatusChange = { viewModel.onAction(BookListAction.OnFormStatusChanged(it)) },
            onRatingChange = { viewModel.onAction(BookListAction.OnFormRatingChanged(it)) },
            sheetState = sheetState,
            onDismiss = { viewModel.onAction(BookListAction.OnDismissForm) },
            onSave = { book -> viewModel.onAction(BookListAction.OnSaveBookClicked(book)) }
        )
    }

    // Backup dialog
    if (uiState.isBackupDialogVisible) {
        BackupDialog(
            onDismiss = { viewModel.onAction(BookListAction.OnDismissBackupDialog) },
            onExportClick = {
                viewModel.onAction(BookListAction.OnDismissBackupDialog)
                exportLauncher.launch("miniestante_backup.json")
            },
            onImportClick = {
                viewModel.onAction(BookListAction.OnDismissBackupDialog)
                importLauncher.launch(arrayOf("application/json", "text/plain", "*/*"))
            }
        )
    }

    // Delete confirmation dialog
    bookToDelete?.let { book ->
        AlertDialog(
            onDismissRequest = { bookToDelete = null },
            title = {
                Text(
                    text = "Excluir livro",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Tem certeza que deseja excluir \"${book.title}\"? Esta ação não pode ser desfeita.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onAction(BookListAction.OnDeleteBookClicked(book))
                        bookToDelete = null
                    }
                ) {
                    Text(
                        text = "Excluir",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { bookToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// Extension functions for file I/O
fun Context.readTextFromUri(uri: Uri): String? {
    return try {
        contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
    } catch (e: Exception) {
        null
    }
}

fun Context.writeTextToUri(uri: Uri, text: String) {
    try {
        contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { it.write(text) }
    } catch (e: Exception) {
        // silently fail — snackbar shown by caller
    }
}

@Preview(showBackground = true, device = "spec:width=390dp,height=844dp")
@Composable
private fun BooksScreenPreview() {
    MiniEstanteTheme {
        // Preview with a mock ViewModel is not straightforward; shown as placeholder
        Text("BooksScreen Preview", modifier = Modifier.padding(16.dp))
    }
}
