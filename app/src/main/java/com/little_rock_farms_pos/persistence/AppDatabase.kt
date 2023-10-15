package com.little_rock_farms_pos.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.little_rock_farms_pos.persistence.daos.CategoryDao
import com.little_rock_farms_pos.persistence.daos.ProductDao
import com.little_rock_farms_pos.persistence.entities.Category
import com.little_rock_farms_pos.persistence.entities.Product


@Database(entities = [Product::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    AppDatabase::class.java,
                    "little-rock-farm-db")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }
        fun destroyInstance() {
            instance = null
        }
    }
}

