package com.kappdev.wordbook.feature_dictionary.presentation.writing

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.feature_dictionary.domain.model.CardTerm
import com.kappdev.wordbook.feature_dictionary.domain.model.Term
import com.kappdev.wordbook.feature_dictionary.domain.use_cases.DictionaryUseCases
import com.kappdev.wordbook.feature_dictionary.domain.util.ImageConverter
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.ProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases
) : ViewModel() {

    private val allCards = mutableStateListOf<CardTerm>()
    private val studiedCards = mutableStateListOf<CardTerm>()
    val unstudiedCards = mutableStateListOf<CardTerm>()

    private val _currentAnswer = mutableStateOf("")
    val currentAnswer: State<String> = _currentAnswer

    private val _currentCard = mutableStateOf<CardTerm?>(null)
    val currentCard: State<CardTerm?> = _currentCard

    private val _answered = mutableStateOf(false)
    val answered: State<Boolean> = _answered

    private val _progress = mutableStateOf(ProgressState())
    val progress: State<ProgressState> = _progress

    private var getTermsJob: Job? = null

    fun launch(setsIds: List<String>) {
        if (allCards.isEmpty()) getCardsBySetId(setsIds)
    }

    fun studyAgain() {
        studiedCards.clear()
        unstudiedCards.clear()
        fillUnstudied()
        updateProgress()
    }

    fun next() {
        if (isAnswerCorrect()) moveToStudied() else nextCard()
        clearAnswer()
    }

    private fun nextCard() {
        currentCard.value?.let { card ->
            unstudiedCards.remove(card)
            unstudiedCards.add(card)
        }
    }

    private fun moveToStudied() {
        currentCard.value?.let { card ->
            studiedCards.add(card)
            unstudiedCards.remove(card)
            updateProgress()
        }
    }

    private fun getCardsBySetId(ids: List<String>) {
        getTermsJob?.cancel()
        getTermsJob = dictionaryUseCases.getTermsByListOfSetsIds(ids).onEach { currentTerms ->
            pasteDataAndSetupValue(currentTerms)
        }.launchIn(scope = viewModelScope)
    }

    private fun pasteDataAndSetupValue(list: List<Term>) {
        allCards.clear()
        list.forEach { term -> addCardTermToLists(term) }
        fillUnstudied()
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

    private fun fillUnstudied() {
        unstudiedCards.clear()
        unstudiedCards.addAll(allCards.shuffled())
    }

    private fun addCardTermToLists(term: Term) {
        val image = getImage(term.image)
        allCards.add(CardTerm(term, image))
    }

    private fun getImage(value: String?): Bitmap? = ImageConverter().stringToBitmap(value)

    private fun clearAnswer() {
        _currentAnswer.value = ""
        _answered.value = false
    }

    fun isAnswerCorrect(): Boolean {
        val correctAnswer = currentCard.value?.term?.term
        return if (correctAnswer == null) false
        else absValue(currentAnswer.value) == absValue(correctAnswer)
    }

    private fun absValue(value: String) = value.trim().lowercase()

    fun isCurrentCard(card: CardTerm): Boolean {
        return card.term.termId == currentCard.value?.term?.termId
    }

    fun answer() { _answered.value = true }
    fun setNewAnswerValue(value: String) { _currentAnswer.value = value }
    fun writeCurrentCard(card: CardTerm) { _currentCard.value = card }
}