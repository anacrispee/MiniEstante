package com.example.miniestante.data.local

import androidx.room.TypeConverter
import com.example.miniestante.data.model.BookRating
import com.example.miniestante.data.model.BookStatus

class Converters {

    @TypeConverter
    fun fromBookStatus(value: BookStatus): String = value.name

    @TypeConverter
    fun toBookStatus(value: String): BookStatus = BookStatus.valueOf(value)

    @TypeConverter
    fun fromBookRating(value: BookRating): String = value.name

    @TypeConverter
    fun toBookRating(value: String): BookRating = BookRating.valueOf(value)
}
