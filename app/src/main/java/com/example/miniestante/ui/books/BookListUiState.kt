package com.example.miniestante.ui.books

import com.example.miniestante.data.model.Book
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus

enum class SortOption {
    START_DATE,
    END_DATE
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
    val formTitle: String = "",
    val formAuthor: String = "",
    val formStartDate: String = "",
    val formEndDate: String = "",
    val formStatus: BookStatus = BookStatus.IN_PROGRESS,
    val formRating: BookRating = BookRating.WORTH_VOTE,
    val isBackupDialogVisible: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
