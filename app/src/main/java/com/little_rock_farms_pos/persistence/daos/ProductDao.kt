package com.little_rock_farms_pos.persistence.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.entities.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()

    @Query("SELECT * from product where product_category_id = :categoryId")
    suspend fun findByCategoryId(categoryId: Int): List<Product>
}