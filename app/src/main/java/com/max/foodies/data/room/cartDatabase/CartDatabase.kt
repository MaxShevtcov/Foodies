package com.max.foodies.data.room.cartDatabase

import ProductInCart
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [ProductInCart::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: CartDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): CartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
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
                    scope.launch { populateDatabase(database.cartDao()) }
                }
            }
        }

        suspend fun populateDatabase(cartDao: CartDao) {
            cartDao.deleteAll()
        }
    }
}