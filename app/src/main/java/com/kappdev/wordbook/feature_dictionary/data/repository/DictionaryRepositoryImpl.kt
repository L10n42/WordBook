package com.kappdev.wordbook.feature_dictionary.data.repository

import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.data.data_source.SetDao
import com.kappdev.wordbook.feature_dictionary.data.data_source.TermDao
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow

class DictionaryRepositoryImpl(
    private val setDao: SetDao,
    private val termDao: TermDao
) : DictionaryRepository {
    override fun getSetsCount(): Long {
        return setDao.getSetsCount()
    }

    override fun getAllSets(): Flow<List<Set>> {
        return setDao.getAllSets()
    }

    override fun getAllTerms(): Flow<List<Term>> {
        return termDao.getAllTerms()
    }

    override fun getSetsList(): List<Set> {
        return setDao.getSetsList()
    }

    override fun getAllTermsBySetIdFlow(setId: String): Flow<List<Term>> {
        return termDao.getAllTermsBySetIdFlow(setId)
    }

    override fun getAllTermsBySetIdList(setId: String): List<Term> {
        return termDao.getAllTermsBySetIdList(setId)
    }

    override fun getAllTermsByArrayOfSetsId(setsId: List<String>): Flow<List<Term>> {
        return termDao.getAllTermsByArrayOfSetsId(setsId)
    }

    override suspend fun incrementSetNumber(setId: String, num: Int) {
        val set = setDao.getSetById(setId)
        set?.let {
            val newSet = set.copy(
                number = set.number + num
            )
            setDao.updateSet(newSet)
        }
    }

    override suspend fun decrementSetNumber(setId: String, num: Int) {
        val set = setDao.getSetById(setId)
        set?.let {
            val newSet = set.copy(
                number = set.number - num
            )
            setDao.updateSet(newSet)
        }
    }

    override fun removeAllTermsBySet(setId: String) {
        termDao.removeAllTermsBySet(setId)
    }

    override fun removeAllTerms() {
        termDao.removeAllTerm()
    }

    override fun removeAllSets() {
        setDao.removeAllSets()
    }

    override fun getSetById(setId: String): Set? {
        return setDao.getSetById(setId)
    }

    override fun getTermById(termId: String): Term? {
        return termDao.getTermById(termId)
    }

    override suspend fun addTerm(term: Term) {
        termDao.addTerm(term)
    }

    override suspend fun addSet(set: Set) {
        setDao.addSet(set)
    }

    override suspend fun updateSet(set: Set) {
        setDao.updateSet(set)
    }

    override suspend fun updateTerm(term: Term) {
        termDao.updateTerm(term)
    }

    override suspend fun removeSet(set: Set) {
        setDao.removeSet(set)
    }

    override suspend fun removeTerm(term: Term) {
        termDao.removeTerm(term)
    }
}