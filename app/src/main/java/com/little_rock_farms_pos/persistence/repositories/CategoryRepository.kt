package com.little_rock_farms_pos.persistence.repositories

import android.app.Application
import com.little_rock_farms_pos.persistence.AppDatabase
import com.little_rock_farms_pos.persistence.daos.CategoryDao
import com.little_rock_farms_pos.persistence.entities.Category

class CategoryRepository(application: Application) {
    private var categoryDao: CategoryDao
    private val database = AppDatabase.getInstance(application)

    init {
        categoryDao = database.categoryDao()
    }

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    suspend fun update(category: Category) {
        categoryDao.update(category)
    }

    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }

    suspend fun findAll(): List<Category> {
        return categoryDao.findAll()
    }
}