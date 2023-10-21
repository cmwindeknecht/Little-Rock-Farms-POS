package com.little_rock_farms_pos.persistence.repositories

import android.app.Application
import com.little_rock_farms_pos.persistence.AppDatabase
import com.little_rock_farms_pos.persistence.daos.ProductDao
import com.little_rock_farms_pos.persistence.entities.Product

class ProductRepository(application: Application) {
    private var productDao: ProductDao
    private val database = AppDatabase.getInstance(application)

    init {
        productDao = database.productDao()
    }

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }

    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    suspend fun deletaAll() {
        productDao.deleteAll()
    }

    suspend fun findByCategoryId(categoryId: Int): List<Product> {
        return productDao.findByCategoryId(categoryId)
    }
}