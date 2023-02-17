package com.kappdev.wordbook.feature_dictionary.domain.repository

import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {

    fun getSetsCount() : Long

    fun getAllSets() : Flow<List<Set>>

    fun getAllTerms() : Flow<List<Term>>

    fun getSetsList() : List<Set>

    fun getAllTermsBySetIdFlow(setId: String) : Flow<List<Term>>

    fun getAllTermsBySetIdList(setId: String) : List<Term>

    fun getAllTermsByArrayOfSetsId(setsId: List<String>) : Flow<List<Term>>

    suspend fun  incrementSetNumber(setId: String, num: Int = 1)

    suspend fun  decrementSetNumber(setId: String, num: Int = 1)

    fun removeAllTermsBySet(setId: String)

    fun removeAllTerms()

    fun removeAllSets()

    fun getSetById(setId: String) : Set?

    fun getTermById(termId: String) : Term?

    suspend fun addTerm(term: Term)

    suspend fun addSet(set: Set)

    suspend fun updateSet(set: Set)

    suspend fun updateTerm(term: Term)

    suspend fun removeSet(set: Set)

    suspend fun removeTerm(term: Term)
}