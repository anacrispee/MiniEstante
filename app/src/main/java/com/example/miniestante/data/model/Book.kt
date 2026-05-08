package com.example.miniestante.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val startDate: String? = null,
    val endDate: String? = null,
    val status: BookStatus = BookStatus.IN_PROGRESS,
    val rating: BookRating = BookRating.NONE,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
