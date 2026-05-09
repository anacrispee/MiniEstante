package com.example.miniestante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.miniestante.data.local.AppDatabase
import com.example.miniestante.data.repository.BookRepository
import com.example.miniestante.ui.books.BookListViewModel
import com.example.miniestante.ui.books.BooksScreen
import com.example.miniestante.ui.theme.MiniEstanteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(applicationContext)
        val repository = BookRepository(db.bookDao())

        setContent {
            MiniEstanteTheme {
                val viewModel: BookListViewModel = viewModel(
                    factory = BookListViewModel.Factory(repository)
                )
                BooksScreen(viewModel = viewModel)
            }
        }
    }
}
