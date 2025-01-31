package com.max.foodies.data.room.roomDatabase

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FoodiesDbModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FoodiesDatabase::class.java,
        "foodies_database"
    ).build()

    @Singleton
    @Provides
    fun provideProductDao(db: FoodiesDatabase) = db.getProductDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: FoodiesDatabase) = db.getCategoryDao()

    @Singleton
    @Provides
    fun provideCartDao(db: FoodiesDatabase) = db.getCartDao()
}