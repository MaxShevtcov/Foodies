package com.max.foodies.data.room.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dbCategory: DbCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dbCategories: List<DbCategory>)

    @Delete
    suspend fun delete(dbCategory: DbCategory)

    @Query("SELECT * FROM dbCategory")
    suspend fun get(): List<DbCategory>

    @Query("DELETE FROM dbCategory")
    suspend fun deleteAll()
}