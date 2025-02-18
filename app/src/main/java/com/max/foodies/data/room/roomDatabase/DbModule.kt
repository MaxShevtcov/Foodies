package com.max.foodies.data.room.roomDatabase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.max.foodies.data.room.ProductConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        productProvider: Provider<ProductDao>,
        categoryProvider: Provider<CategoryDao>,
        cartProvider: Provider<CartDao>,
    ): FoodiesDatabase {
        val instance = Room.databaseBuilder(
            appContext,
            FoodiesDatabase::class.java,
            "foodies_database"
        )
            .addTypeConverter(ProductConverters())
            .addCallback(
                FoodiesDatabaseCallback(
                    productProvider,
                    categoryProvider,
                    cartProvider
                )
            )
            .build()

        return instance
    }

    @Provides
    @Singleton
    fun provideProductDao(db: FoodiesDatabase) = db.productDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: FoodiesDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideCartDao(db: FoodiesDatabase) = db.cartDao()
}

private class FoodiesDatabaseCallback(
    private val productProvider: Provider<ProductDao>,
    private val categoryProvider: Provider<CategoryDao>,
    private val cartProvider: Provider<CartDao>,
) : RoomDatabase.Callback() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        applicationScope.launch {
            populateDatabase()
        }
    }

    suspend fun populateDatabase() {
        productProvider.get().deleteAll()
        categoryProvider.get().deleteAll()
    }
}


