package com.max.foodies.data.room.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.max.foodies.data.room.ProductConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [DbProduct::class, DbCategory::class, DbCartCounter::class],
    version = 1,
    exportSchema = false
)
abstract class FoodiesDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getCartDao(): CartDao

}