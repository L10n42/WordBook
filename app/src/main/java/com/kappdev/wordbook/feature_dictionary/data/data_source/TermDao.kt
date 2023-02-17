package com.kappdev.wordbook.feature_dictionary.data.data_source

import androidx.room.*
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import kotlinx.coroutines.flow.Flow

@Dao
interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTerm(term: Term)

    @Query("SELECT * FROM terms_table")
    fun getAllTerms() : Flow<List<Term>>

    @Query("SELECT * FROM terms_table WHERE set_id = :setId")
    fun getAllTermsBySetIdFlow(setId: String) : Flow<List<Term>>

    @Query("SELECT * FROM terms_table WHERE set_id = :setId")
    fun getAllTermsBySetIdList(setId: String) : List<Term>

    @Query("SELECT * FROM terms_table WHERE set_id IN (:setsId)")
    fun getAllTermsByArrayOfSetsId(setsId: List<String>) : Flow<List<Term>>

    @Query("SELECT * FROM terms_table WHERE term_id = :id LIMIT 1")
    fun getTermById(id: String) : Term?

    @Update
    suspend fun updateTerm(term: Term)

    @Delete
    suspend fun removeTerm(term: Term)

    @Query("DELETE FROM terms_table WHERE set_id = :setId")
    fun removeAllTermsBySet(setId: String)

    @Query("DELETE FROM terms_table")
    fun removeAllTerm()

}