package com.kappdev.wordbook.feature_dictionary.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.kappdev.wordbook.feature_dictionary.domain.model.Set

@Dao
interface SetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSet(set: Set)

    @Query("SELECT * FROM sets_table")
    fun getAllSets() : Flow<List<Set>>

    @Query("SELECT * FROM sets_table")
    fun getSetsList(): List<Set>

    @Query("SELECT * FROM sets_table WHERE set_id = :id LIMIT 1")
    fun getSetById(id: String) : Set?

    @Query("SELECT COUNT(set_id) FROM sets_table")
    fun getSetsCount() : Long

    @Update
    suspend fun updateSet(set: Set)

    @Delete
    suspend fun removeSet(set: Set)

    @Query("DELETE FROM sets_table")
    fun removeAllSets()

}