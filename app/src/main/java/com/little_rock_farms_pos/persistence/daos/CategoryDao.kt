package com.little_rock_farms_pos.persistence.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.little_rock_farms_pos.persistence.entities.Category

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category) {}

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM category")
    fun findAll(): List<Category>
}