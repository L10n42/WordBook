package com.kappdev.wordbook.feature_dictionary.presentation.tests

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kappdev.wordbook.core.domain.util.term_to_speech.TermToSpeech
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
class TestsViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases,
    private val termToSpeech: TermToSpeech
) : ViewModel() {

    private val allCards = mutableStateListOf<CardTerm>()
    private val studiedCards = mutableStateListOf<CardTerm>()
    val unstudiedCards = mutableStateListOf<CardTerm>()

    private val _answers = mutableStateOf<List<CardTerm>>(emptyList())
    val answers: State<List<CardTerm>> = _answers

    private val _currentCard = mutableStateOf<CardTerm?>(null)
    val currentCard: State<CardTerm?> = _currentCard

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

    fun currentCardToStudied() {
        currentCard.value?.let { card ->
            studiedCards.add(card)
            unstudiedCards.remove(card)
            updateProgress()
        }
    }

    fun shuffleUnstudied() {
        val copy = unstudiedCards.shuffled()
        unstudiedCards.clear()
        unstudiedCards.addAll(copy)
    }

    fun generateAnswers() {
        currentCard.value?.let { card ->
            val newAnswers = mutableListOf(card)
            while (newAnswers.size < 4) {
                val randomCard = allCards.random()
                if (randomCard !in newAnswers)
                    newAnswers.add(randomCard)
            }
            _answers.value = newAnswers.shuffled()
        }
    }

    fun say(text: String) {
        termToSpeech.say(text)
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

    fun isAnswerRight(answer: CardTerm) = isCurrentCard(answer)
    fun isCurrentCard(card: CardTerm): Boolean {
        return card.term.termId == currentCard.value?.term?.termId
    }

    fun writeCurrentCard(card: CardTerm) {
        _currentCard.value = card
    }
}