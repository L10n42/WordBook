package com.kappdev.wordbook.feature_dictionary.presentation.terms

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.core.domain.util.term_to_speech.TermToSpeech
import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.DictionaryUseCases
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.SortCards
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.TermsSearch
import com.kappdev.wordbook.feature_dictionary.domain.util.ImageConverter
import com.kappdev.wordbook.feature_dictionary.domain.util.OrderType
import com.kappdev.wordbook.feature_dictionary.domain.util.TermOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val termToSpeech: TermToSpeech,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val setId = savedStateHandle.get<String>("setId")
    var lastSearchValue = ""

    private val _termsOrder = mutableStateOf<TermOrder>(TermOrder.Term(OrderType.Ascending))
    val termsOrder: State<TermOrder> = _termsOrder

    private val _currentSet = mutableStateOf<Set?>(null)
    val currentSet: State<Set?> = _currentSet

    private val _sets = mutableStateOf<List<Set>>(emptyList())
    val sets: State<List<Set>> = _sets

    private val _isListEmpty = mutableStateOf(false)
    val isListEmpty: State<Boolean> = _isListEmpty

    private val _currentTopBar = mutableStateOf<TermsTopBar>(TermsTopBar.Default)
    val currentTopBar: State<TermsTopBar> = _currentTopBar

    private val _currentDialog = mutableStateOf<TermsDialog?>(null)
    val currentDialog: State<TermsDialog?> = _currentDialog

    private val _isListLoading = mutableStateOf(false)
    val isListLoading: State<Boolean> = _isListLoading

    val selectedList = mutableStateListOf<Term>()
    val cardTerms = mutableStateListOf<CardTerm>()
    val cardTermSearchList = mutableStateListOf<CardTerm>()

    private var getTermsJob: Job? = null
    private var getSetsJob: Job? = null

    fun changeOrderAndGetTerms(newOrder: TermOrder) {
        _termsOrder.value = newOrder
        sortCards(termsOrder.value)
    }

    private fun sortCards(order: TermOrder) {
        val newOrderedList = SortCards().invoke(cardTerms, order)
        cardTerms.clear()
        cardTerms.addAll(newOrderedList)
    }

    fun onLaunch() {
        if (setId != null && setId.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                getTermsBySetId(setId)
                getSets()
                setCurrentSet(dictionaryUseCases.getSetById(setId))
            }
        }
    }

    fun removeSingleTerm(term: Term) {
        viewModelScope.launch(Dispatchers.IO) {
            dictionaryUseCases.removeTerm(term)
        }
    }

    fun removeMultipleTerms() {
        viewModelScope.launch(Dispatchers.IO) {
            startListLoad()
            dictionaryUseCases.removeTerm(terms = selectedList)
            setTopBar(TermsTopBar.Default)
            clearSelected()
        }
    }

    fun search(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            cardTermSearchList.clear()
            val searchResult = TermsSearch().searchIn(cardTerms, value)
            lastSearchValue = value
            cardTermSearchList.addAll(searchResult)
        }
    }

    fun say(value: String) {
        termToSpeech.say(value)
    }

    fun singleMoveTo(setId: String, term: Term) {
        viewModelScope.launch(Dispatchers.IO) {
            startListLoad()
            dictionaryUseCases.moveTermToSet(setId, term)
        }
    }

    fun multipleMoveTo(setId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startListLoad()
            dictionaryUseCases.moveTermToSet(setId, selectedList)
            setTopBar(TermsTopBar.Default)
            clearSelected()
        }
    }

    private fun getTermsBySetId(setId: String) {
        getTermsJob?.cancel()
        getTermsJob = dictionaryUseCases.getTermsFlowBySetId(setId).onEach { currentTerms ->
            startListLoad()
            pasteDataAndSetupValue(currentTerms)
        }.launchIn(scope = viewModelScope)
    }

    private fun pasteDataAndSetupValue(list: List<Term>) {
        cardTerms.clear()
        list.forEach { term -> addCardTermToLists(term) }
        _isListEmpty.value = list.isEmpty()
        sortCards(termsOrder.value)
        research()
        finishListLoad()
    }

    private fun addCardTermToLists(term: Term) {
        val image = getImage(term.image)
        cardTerms.add(CardTerm(term, image))
    }

    private fun getImage(value: String?) : Bitmap? = ImageConverter().stringToBitmap(value)

    private fun research() {
        search(lastSearchValue)
    }

    private fun getSets() {
        getSetsJob?.cancel()
        getSetsJob = dictionaryUseCases.getAllSets.flow().onEach { sets ->
            _sets.value = sets
        }.launchIn(scope = viewModelScope)
    }

    fun selectTerm(term: Term) { selectedList.add(term) }
    fun deselectTerm(term: Term) { selectedList.remove(term) }
    fun clearSelected() { selectedList.clear() }

    fun showDialog(dialog: TermsDialog) { _currentDialog.value = dialog }
    fun closeDialog() { _currentDialog.value = null }

    fun setTopBar(topBar: TermsTopBar) { _currentTopBar.value = topBar }

    private fun setCurrentSet(set: Set?) { _currentSet.value = set }

    private fun startListLoad() { _isListLoading.value = true }
    private fun finishListLoad() { _isListLoading.value = false }
}