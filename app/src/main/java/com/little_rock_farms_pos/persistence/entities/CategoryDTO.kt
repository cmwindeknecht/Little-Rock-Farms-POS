package com.little_rock_farms_pos.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

data class CategoryDTO(
    val categoryId: Int,
    val categoryName: String,
    var products: List<Product>
)