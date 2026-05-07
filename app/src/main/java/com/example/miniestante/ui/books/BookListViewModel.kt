package com.example.miniestante.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.miniestante.data.model.Book
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus
import com.example.miniestante.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BookListViewModel(private val repository: BookRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState: StateFlow<BookListUiState> = _uiState.asStateFlow()

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    init {
        viewModelScope.launch {
            repository.books.collect { books ->
                _uiState.update { state ->
                    state.copy(
                        books = books,
                        filteredBooks = applyFilters(
                            books,
                            state.searchQuery,
                            state.selectedStatusFilter,
                            state.selectedRatingFilter,
                            state.selectedSortOption
                        )
                    )
                }
            }
        }
    }

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnSearchChanged -> updateSearch(action.query)
            is BookListAction.OnStatusFilterSelected -> updateStatusFilter(action.status)
            is BookListAction.OnRatingFilterSelected -> updateRatingFilter(action.rating)
            is BookListAction.OnSortSelected -> updateSort(action.sort)
            is BookListAction.OnAddBookClicked -> _uiState.update { it.copy(isBookFormVisible = true, editingBook = null) }
            is BookListAction.OnEditBookClicked -> _uiState.update { it.copy(isBookFormVisible = true, editingBook = action.book) }
            is BookListAction.OnDeleteBookClicked -> deleteBook(action.book)
            is BookListAction.OnSaveBookClicked -> saveBook(action.book)
            is BookListAction.OnDismissForm -> _uiState.update { it.copy(isBookFormVisible = false, editingBook = null) }
            is BookListAction.OnBackupClicked -> _uiState.update { it.copy(isBackupDialogVisible = true) }
            is BookListAction.OnExportJsonClicked -> exportJson()
            is BookListAction.OnImportJsonClicked -> importJson(action.jsonString)
            is BookListAction.OnDismissBackupDialog -> _uiState.update { it.copy(isBackupDialogVisible = false) }
            is BookListAction.OnClearMessages -> _uiState.update { it.copy(errorMessage = null, successMessage = null) }
        }
    }

    private fun updateSearch(query: String) {
        _uiState.update { state ->
            val filtered = applyFilters(state.books, query, state.selectedStatusFilter, state.selectedRatingFilter, state.selectedSortOption)
            state.copy(searchQuery = query, filteredBooks = filtered)
        }
    }

    private fun updateStatusFilter(status: BookStatus?) {
        _uiState.update { state ->
            val filtered = applyFilters(state.books, state.searchQuery, status, state.selectedRatingFilter, state.selectedSortOption)
            state.copy(selectedStatusFilter = status, filteredBooks = filtered)
        }
    }

    private fun updateRatingFilter(rating: BookRating?) {
        _uiState.update { state ->
            val filtered = applyFilters(state.books, state.searchQuery, state.selectedStatusFilter, rating, state.selectedSortOption)
            state.copy(selectedRatingFilter = rating, filteredBooks = filtered)
        }
    }

    private fun updateSort(sort: SortOption) {
        _uiState.update { state ->
            val filtered = applyFilters(state.books, state.searchQuery, state.selectedStatusFilter, state.selectedRatingFilter, sort)
            state.copy(selectedSortOption = sort, filteredBooks = filtered)
        }
    }

    private fun saveBook(book: Book) {
        viewModelScope.launch {
            val isEditing = _uiState.value.books.any { it.id == book.id }
            if (isEditing) {
                repository.updateBook(book.copy(updatedAt = System.currentTimeMillis()))
            } else {
                repository.addBook(book)
            }
            _uiState.update { it.copy(isBookFormVisible = false, editingBook = null) }
        }
    }

    private fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    private fun exportJson(): String {
        val books = _uiState.value.books
        return try {
            val jsonString = json.encodeToString(books)
            _uiState.update { it.copy(successMessage = "Exportação pronta! ${books.size} livro(s).") }
            jsonString
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = "Erro ao exportar: ${e.message}") }
            ""
        }
    }

    fun getBooksAsJson(): String {
        return try {
            json.encodeToString(_uiState.value.books)
        } catch (e: Exception) {
            ""
        }
    }

    private fun importJson(jsonString: String) {
        viewModelScope.launch {
            try {
                val books = json.decodeFromString<List<Book>>(jsonString)
                if (books.isEmpty()) {
                    _uiState.update { it.copy(errorMessage = "Nenhum livro encontrado no arquivo.") }
                    return@launch
                }
                repository.replaceAll(books)
                _uiState.update {
                    it.copy(
                        isBackupDialogVisible = false,
                        successMessage = "${books.size} livro(s) importado(s) com sucesso!"
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Arquivo inválido. Verifique o formato JSON.") }
            }
        }
    }

    private fun applyFilters(
        books: List<Book>,
        query: String,
        status: BookStatus?,
        rating: BookRating?,
        sort: SortOption
    ): List<Book> {
        return books
            .filter { book ->
                if (query.isBlank()) true
                else book.title.contains(query, ignoreCase = true) || book.author.contains(query, ignoreCase = true)
            }
            .filter { book -> if (status == null) true else book.status == status }
            .filter { book -> if (rating == null) true else book.rating == rating }
            .sortedWith(
                when (sort) {
                    SortOption.START_DATE -> compareByDescending { it.startDate ?: "" }
                    SortOption.END_DATE -> compareByDescending { it.endDate ?: "" }
                }
            )
    }

    class Factory(private val repository: BookRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookListViewModel(repository) as T
        }
    }
}
