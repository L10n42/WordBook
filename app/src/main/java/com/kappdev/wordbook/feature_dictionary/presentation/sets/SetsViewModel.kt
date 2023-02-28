package com.kappdev.wordbook.feature_dictionary.presentation.sets

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.repository.SettingsRepository
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.DictionaryUseCases
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.ImExDatabaseUseCases
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.SetsSearch
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.SortSets
import com.kappdev.wordbook.feature_dictionary.domain.util.OrderType
import com.kappdev.wordbook.feature_dictionary.domain.util.SetOrder
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarState
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetsViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val imExDatabaseUseCases: ImExDatabaseUseCases,
    private val settings: SettingsRepository
) : ViewModel() {
    var lastSearchValue = ""

    private val _isListLoading = mutableStateOf(false)
    val isListLoading: State<Boolean> = _isListLoading

    private val _isListEmpty = mutableStateOf(false)
    val isListEmpty: State<Boolean> = _isListEmpty

    private val _currentTopBar = mutableStateOf<SetsTopBar>(SetsTopBar.Default)
    val currentTopBar: State<SetsTopBar> = _currentTopBar

    private val _currentDialog = mutableStateOf<SetsDialog?>(null)
    val currentDialog: State<SetsDialog?> = _currentDialog

    private val _snackbarState = mutableStateOf(SnackbarState())
    val snackbarState: State<SnackbarState> = _snackbarState

    private val _setsOrder = mutableStateOf(settings.getSetsOrder())
    val setsOrder: State<SetOrder> = _setsOrder

    val sets = mutableStateListOf<Set>()
    val setsSearchList = mutableStateListOf<Set>()
    val selectedList = mutableStateListOf<Set>()

    private var getSetsJob: Job? = null

    init { getSets() }

    fun getSelectedIds() : List<String> {
        return selectedList.map { set ->
            set.setId
        }
    }

    fun changeOrderAndGetSets(newOrder: SetOrder) {
        _setsOrder.value = newOrder
        settings.setSetsOrder(newOrder)
        sortSets(setsOrder.value)
    }

    private fun sortSets(order: SetOrder) {
        val newOrderedList = SortSets().invoke(sets, order)
        sets.clear()
        sets.addAll(newOrderedList)
    }

    fun search(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setsSearchList.clear()
            val searchResult = SetsSearch().searchIn(sets, value)
            lastSearchValue = value
            setsSearchList.addAll(searchResult)
        }
    }

    fun onEvent(event: SetsEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when(event) {
                is SetsEvent.AddSet -> dictionaryUseCases.addSet(event.set)
                is SetsEvent.RemoveSet -> dictionaryUseCases.removeSet(event.set)
                is SetsEvent.UpdateSet -> dictionaryUseCases.updateSet(event.set)
            }
        }
    }

    fun exportSet(setId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imExDatabaseUseCases.exportSet(setId)
        }
    }

    fun removeSelectedSets() {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            dictionaryUseCases.removeSet(sets = selectedList)
            setTopBar(SetsTopBar.Default)
            clearSelected()
        }
    }

    private fun getSets() {
        getSetsJob?.cancel()
        getSetsJob = dictionaryUseCases.getAllSets.flow().onEach { newList ->
            startLoading()
            fillSetsList(newList)
            _isListEmpty.value = sets.isEmpty()
            sortSets(setsOrder.value)
            research()
            cancelLoading()
        }.launchIn(scope = viewModelScope)
    }

    private fun fillSetsList(list: List<Set>) {
        sets.clear()
        sets.addAll(list)
    }

    private fun research() = search(lastSearchValue)

    fun selectSet(set: Set) { selectedList.add(set) }
    fun deselectSet(set: Set) { selectedList.remove(set) }
    fun clearSelected() { selectedList.clear() }

    fun showDialog(dialog: SetsDialog) { _currentDialog.value = dialog }
    fun closeDialog() { _currentDialog.value = null }

    fun setTopBar(topBar: SetsTopBar) { _currentTopBar.value = topBar }

    fun makeSnackbar(message: String, type: SnackbarType) {
        _snackbarState.value = SnackbarState(isVisible = true, message, type)
    }
    fun setSnackbarVisibility(isVisible: Boolean) {
        _snackbarState.value = snackbarState.value.copy(isVisible = isVisible)
    }

    private fun startLoading() { _isListLoading.value = true }
    private fun cancelLoading() { _isListLoading.value = false }
}