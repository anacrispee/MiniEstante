package com.example.miniestante.ui.books

import com.example.miniestante.data.model.Book
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus

enum class SortOption(val label: String) {
    START_DATE("Início"),
    END_DATE("Fim")
}

data class BookListUiState(
    val books: List<Book> = emptyList(),
    val filteredBooks: List<Book> = emptyList(),
    val searchQuery: String = "",
    val selectedStatusFilter: BookStatus? = null,
    val selectedRatingFilter: BookRating? = null,
    val selectedSortOption: SortOption = SortOption.START_DATE,
    val isLoading: Boolean = false,
    val isBookFormVisible: Boolean = false,
    val editingBook: Book? = null,
    val isBackupDialogVisible: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
