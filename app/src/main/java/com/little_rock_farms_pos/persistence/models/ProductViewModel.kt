package com.little_rock_farms_pos.persistence.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.entities.Product
import com.little_rock_farms_pos.persistence.repositories.CategoryRepository
import com.little_rock_farms_pos.persistence.repositories.ProductRepository

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = ProductRepository(app)

    suspend fun insert(product: Product) {
        repository.insert(product)
    }

    suspend fun update(product: Product) {
        repository.update(product)
    }

    suspend fun delete(product: Product) {
        repository.delete(product)
    }
}