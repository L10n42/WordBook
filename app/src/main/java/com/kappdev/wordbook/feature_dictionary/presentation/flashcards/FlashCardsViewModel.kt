package com.kappdev.wordbook.feature_dictionary.presentation.flashcards

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.core.domain.util.term_to_speech.TermToSpeech
import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.DictionaryUseCases
import com.kappdev.wordbook.feature_dictionary.domain.util.FlashCardsOptionsImpl
import com.kappdev.wordbook.feature_dictionary.domain.util.ImageConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FlashCardsViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val flashCardsOptions: FlashCardsOptionsImpl,
    private val termToSpeech: TermToSpeech
) : ViewModel() {

    private val allCards = mutableStateListOf<CardTerm>()
    private val studiedCards = mutableStateListOf<CardTerm>()
    val unstudiedCards = mutableStateListOf<CardTerm>()

    private val _progress = mutableStateOf(ProgressState())
    val progress: State<ProgressState> = _progress

    private val _options = mutableStateOf(FlashCardsOptions())
    val options: State<FlashCardsOptions> = _options

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var getTermsJob: Job? = null

    fun launch(ids: List<String>) {
        if (allCards.isEmpty()) getCardsBySetId(ids)
        _options.value = flashCardsOptions.getAllOptions()
    }

    private fun resetUnstudiedCards() {
        val newList = unstudiedCards.shuffled()
        unstudiedCards.clear()
        unstudiedCards.addAll(newList)
    }

    fun switchImageVisibility() {
        val isImageVisible = !options.value.isImageVisible
        _options.value = options.value.copy(isImageVisible = isImageVisible)
        flashCardsOptions.setImageVisibility(isImageVisible)
    }

    fun switchExampleVisibility() {
        val isExamplesVisible = !options.value.isExampleVisible
        _options.value = options.value.copy(isExampleVisible = isExamplesVisible)
        flashCardsOptions.setExamplesVisibility(isExamplesVisible)
    }

    fun switchCardRotation() {
        val cardRotation = when(options.value.cardRotation) {
            CardRotation.Horizontal -> CardRotation.Vertical
            CardRotation.Vertical -> CardRotation.Horizontal
        }
        _options.value = options.value.copy(cardRotation = cardRotation)
        flashCardsOptions.setCardRotation(cardRotation)
    }

    fun switchReversCard() {
        val reversedReview = !options.value.reversedReview
        _options.value = options.value.copy(reversedReview = reversedReview)
        flashCardsOptions.setReversedReview(reversedReview)
        resetUnstudiedCards()
    }

    fun reset() {
        studiedCards.clear()
        unstudiedCards.clear()
        fillUnstudied()
        updateProgress()
    }

    fun say(text: String) {
        termToSpeech.say(text)
    }

    fun switchCard(card: CardTerm) {
        unstudiedCards.remove(card)
        unstudiedCards.add(card)
    }

    fun moveCardToStudied(card: CardTerm) {
        studiedCards.add(card)
        unstudiedCards.remove(card)
        updateProgress()
    }

    private fun updateProgress() {
        _progress.value = ProgressState(
            all = allCards.size,
            studied = studiedCards.size,
            unstudied = unstudiedCards.size,
            progressValue = studiedCards.size.toFloat() / allCards.size.toFloat()
        )
    }

    private fun getCardsBySetId(ids: List<String>) {
        getTermsJob?.cancel()
        getTermsJob = dictionaryUseCases.getTermsByListOfSetsIds(ids).onEach { currentTerms ->
            startLoading()
            pasteDataAndSetupValue(currentTerms)
        }.launchIn(scope = viewModelScope)
    }

    private fun pasteDataAndSetupValue(list: List<Term>) {
        allCards.clear()
        list.forEach { term -> addCardTermToLists(term) }
        fillUnstudied()
        updateProgress()
        cancelLoading()
    }

    private fun addCardTermToLists(term: Term) {
        val image = getImage(term.image)
        allCards.add(CardTerm(term, image))
    }

    private fun getImage(value: String?) : Bitmap? {
        return ImageConverter().stringToBitmap(value)
    }

    private fun startLoading() { _isLoading.value = true }
    private fun cancelLoading() { _isLoading.value = false }

    private fun fillUnstudied() {
        unstudiedCards.clear()
        unstudiedCards.addAll(allCards.shuffled())
    }
}