package com.little_rock_farms_pos.persistence.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.little_rock_farms_pos.persistence.entities.Product

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product) {}

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}