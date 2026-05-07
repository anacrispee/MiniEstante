package com.example.miniestante.ui.books

import com.example.miniestante.data.model.Book
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus

sealed class BookListAction {
    data class OnSearchChanged(val query: String) : BookListAction()
    data class OnStatusFilterSelected(val status: BookStatus?) : BookListAction()
    data class OnRatingFilterSelected(val rating: BookRating?) : BookListAction()
    data class OnSortSelected(val sort: SortOption) : BookListAction()
    object OnAddBookClicked : BookListAction()
    data class OnEditBookClicked(val book: Book) : BookListAction()
    data class OnDeleteBookClicked(val book: Book) : BookListAction()
    data class OnSaveBookClicked(val book: Book) : BookListAction()
    object OnDismissForm : BookListAction()
    object OnBackupClicked : BookListAction()
    data class OnExportJsonClicked(val jsonString: String) : BookListAction()
    data class OnImportJsonClicked(val jsonString: String) : BookListAction()
    object OnDismissBackupDialog : BookListAction()
    object OnClearMessages : BookListAction()
}
