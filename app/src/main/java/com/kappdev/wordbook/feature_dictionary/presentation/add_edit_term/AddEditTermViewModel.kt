package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.feature_dictionary.data.util.IDGenerator
import com.kappdev.wordbook.feature_dictionary.domain.model.Set
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.DictionaryUseCases
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarState
import com.kappdev.wordbook.feature_dictionary.domain.util.SnackbarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTermViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val editTermId = savedStateHandle.get<String>("termId")
    private val setId = savedStateHandle.get<String>("setId")
    val action: TermAction = if (editTermId != null && editTermId.isNotBlank()) TermAction.EDIT else TermAction.ADD
    var sets: List<Set> = emptyList()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isImageLoading = mutableStateOf(false)
    val isImageLoading: State<Boolean> = _isImageLoading

    private val _selectedItem = mutableStateOf<Set?>(null)
    val selectedItem: State<Set?> = _selectedItem

    private val _snackbarState = mutableStateOf(SnackbarState())
    val snackbarState: State<SnackbarState> = _snackbarState

    private val _fieldsState = mutableStateOf(FieldsState())
    val fieldsState: State<FieldsState> = _fieldsState

    init { getAllSets() }

    fun getImageFromStorage(uri: Uri) {
        loadImage { dictionaryUseCases.getImageFromStorage(uri) }
    }
    fun getImageFromUrl(url: String) {
        loadImage { dictionaryUseCases.getImageFromUrl(url) }
    }

    private fun loadImage(getImage: suspend () -> String?) {
        viewModelScope.launch(Dispatchers.IO) {
            isImageLoading(true)
            try {
                setImage(getImage())
            } catch (e: Exception) {
                makeError(e.message.toString())
            }finally {
                isImageLoading(false)
            }
        }
    }

    fun onLaunch(onWrongData: () -> Unit) {
        if (editTermId != null && editTermId.isNotBlank()) getTerm(editTermId)

        if (setId != null && setId.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                val set = dictionaryUseCases.getSetById(setId)
                set?.let {
                    setSelectedSet(set)
                }
            }
        } else onWrongData()
    }

    private fun getTerm(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val term = dictionaryUseCases.getTermById(id)
            term?.let { fillFieldByTerm(term) }
        }
    }

    private fun getAllSets() {
        viewModelScope.launch(Dispatchers.IO) {
            sets = dictionaryUseCases.getAllSets.list()
        }
    }

    fun insertTerm(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            selectedItem.value?.let { set ->
                val termId = if (action == TermAction.ADD) IDGenerator().generateID() else editTermId?: ""
                val term = Term(
                    setId = set.setId,
                    termId = termId,
                    term = fieldsState.value.term,
                    definition = fieldsState.value.definition,
                    example = fieldsState.value.example,
                    image = fieldsState.value.image,
                    transcription = fieldsState.value.transcription,
                    timestamp = System.currentTimeMillis()
                )
                try {
                    dictionaryUseCases.insertTerm(term, action)
                    launch(Dispatchers.Main) { onSuccess() }
                } catch (e: Exception) {
                    makeError(e.message.toString())
                }
            }
        }
    }

    fun proposeTermMeaning() {
        viewModelScope.launch(Dispatchers.IO) {
            startLoading()
            try {
                val result = dictionaryUseCases.proposeTermMeaning(fieldsState.value.term)
                pasteProposeMeaningResultToField(result)
            } catch (e: Exception) {
                makeError(e.message.toString())
            } finally {
                finishLoading()
            }
        }
    }

    private fun pasteProposeMeaningResultToField(term: Term) {
        _fieldsState.value = fieldsState.value.copy(
            transcription = term.transcription,
            definition = term.definition,
            example = term.example
        )
    }

    private fun makeError(message: String) {
        _snackbarState.value = SnackbarState(
            isVisible = true,
            message = message,
            snackbarType = SnackbarType.Error
        )
    }

    private fun fillFieldByTerm(term: Term) {
        _fieldsState.value = FieldsState(
            term = term.term,
            transcription = term.transcription,
            definition = term.definition,
            example = term.example,
            image = term.image
        )
    }

    fun makeSnackbarInvisibility() {
        _snackbarState.value = snackbarState.value.copy(isVisible = false)
    }
    fun setSelectedSet(set: Set) { _selectedItem.value = set }
    private fun isImageLoading(isLoading: Boolean) { _isImageLoading.value = isLoading }

    fun setTerm(value: String) {
        _fieldsState.value = fieldsState.value.copy(term = value)
    }
    fun setTranscription(value: String) {
        _fieldsState.value = fieldsState.value.copy(transcription = value)
    }
    fun setDefinition(value: String) {
        _fieldsState.value = fieldsState.value.copy(definition = value)
    }
    fun setExample(value: String) {
        _fieldsState.value = fieldsState.value.copy(example = value)
    }
    fun setImage(image: String?) {
        _fieldsState.value = fieldsState.value.copy(image = image)
    }

    private fun startLoading() { _isLoading.value = true }
    private fun finishLoading() { _isLoading.value = false }
}