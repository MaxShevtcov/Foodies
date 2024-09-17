package com.max.foodies.data.room.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.max.foodies.data.room.ProductConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DbProduct::class, DbCategory::class], version = 1, exportSchema = false)
abstract class FoodiesDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    companion object {
        @Volatile
        private var INSTANCE: FoodiesDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): FoodiesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodiesDatabase::class.java,
                    "foodies_database"
                )
                    .addTypeConverter(ProductConverters())
                    .addCallback(FoodiesDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class FoodiesDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let {database -> scope.launch { populateDatabase(database.productDao(), database.categoryDao()) } }
            }
        }

        suspend fun populateDatabase(productDao: ProductDao, categoryDao: CategoryDao) {
            productDao.deleteAll()
            categoryDao.deleteAll()
        }
    }
}