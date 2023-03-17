package com.aek.kotlinmvvm.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aek.kotlinmvvm.model.Country

@Dao
interface CountryDAO {

    @Query("SELECT * FROM country")
    suspend fun getAll(): List<Country>

    @Query("SELECT * FROM country WHERE id = :id")
    suspend fun getWithId(id: Int): Country

    @Insert
    suspend fun insertAll(vararg counties: Country): List<Long>

    @Query("DELETE FROM country")
    suspend fun deleteAll()

    @Query("DELETE FROM country WHERE id = :id")
    suspend fun deleteWithId(id: Int)
}
