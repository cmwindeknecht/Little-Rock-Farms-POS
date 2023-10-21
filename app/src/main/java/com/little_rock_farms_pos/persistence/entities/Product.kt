package com.little_rock_farms_pos.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(entity = Category::class,
        parentColumns = arrayOf("category_id"),
        childColumns = arrayOf("product_category_id"),
        onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(value = ["product_category_id"])]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val productId: Int? = null,
    @ColumnInfo(name = "product_name")
    val productName: String,
    @ColumnInfo(name = "product_price")
    var productPrice: Double,
    @ColumnInfo(name = "product_category_id")
    val productCategoryId: Int
)