package com.little_rock_farms_pos.persistence.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.repositories.CategoryRepository

class CategoryViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = CategoryRepository(app)

    suspend fun insert(category: Category) {
        repository.insert(category)
    }

    suspend fun update(category: Category) {
        repository.update(category)
    }

    suspend fun delete(category: Category) {
        repository.delete(category)
    }

    suspend fun findAll(): List<Category> {
        return repository.findAll()
    }
}