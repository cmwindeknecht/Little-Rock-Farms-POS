package com.little_rock_farms_pos.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(
    tableName = "category",
    indices = [Index(value = ["category_id"], unique = true)]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val categoryId: Int? = null,
    @ColumnInfo(name = "category_name")
    val categoryName: String
)