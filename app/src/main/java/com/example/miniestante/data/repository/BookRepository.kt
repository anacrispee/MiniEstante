package com.example.miniestante.data.repository

import com.example.miniestante.data.local.BookDao
import com.example.miniestante.data.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {

    val books: Flow<List<Book>> = dao.getAllBooks()

    suspend fun addBook(book: Book) = dao.insertBook(book)

    suspend fun updateBook(book: Book) = dao.updateBook(book)

    suspend fun deleteBook(book: Book) = dao.deleteBook(book)

    suspend fun replaceAll(books: List<Book>) {
        dao.deleteAll()
        dao.insertAll(books)
    }
}
