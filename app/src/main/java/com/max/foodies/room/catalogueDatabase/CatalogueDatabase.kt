package com.max.foodies.room.catalogueDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.max.foodies.room.ProductConverters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ProductInCatalogue::class], version = 1, exportSchema = false)
abstract class CatalogueDatabase : RoomDatabase() {

    abstract fun catalogueDao(): CatalogueDao

    companion object {
        @Volatile
        private var INSTANCE: CatalogueDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): CatalogueDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatalogueDatabase::class.java,
                    "catalogue_database"
                )
                    .addTypeConverter(ProductConverters())
                    .addCallback(CartDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class CartDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch { populateDatabase(database.catalogueDao()) }
                }
            }
        }

        suspend fun populateDatabase(catalogueDao: CatalogueDao) {
            catalogueDao.deleteAll()
        }
    }
}