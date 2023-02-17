package com.kappdev.wordbook.feature_dictionary.presentation.tests.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kappdev.wordbook.feature_dictionary.presentation.tests.TestsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnswerFields(
    modifier: Modifier = Modifier,
    viewModel: TestsViewModel,
    reversedReview: Boolean
) {
    val defaultFieldsState = listOf(AnswerFieldState.DEFAULT, AnswerFieldState.DEFAULT, AnswerFieldState.DEFAULT, AnswerFieldState.DEFAULT)
    var answerState by rememberSaveable { mutableStateOf(defaultFieldsState) }
    val answers = viewModel.answers.value

    fun setAnswerState(index: Int, value: AnswerFieldState) {
        answerState = answerState.mapIndexed { listIndex, answerFieldState ->
            if (listIndex == index) value else answerFieldState
        }
    }

    fun clearAnswers() {
        answerState = defaultFieldsState
    }

    val scope = rememberCoroutineScope()
    fun next() {
        scope.launch {
            delay(1_000)
            viewModel.currentCardToStudied()
            clearAnswers()
        }
    }

    fun checkField(index: Int) {
        if (answerState[index] == AnswerFieldState.DEFAULT) {
            if (viewModel.isAnswerRight(answers[index])) {
                setAnswerState(index, AnswerFieldState.CORRECT)
                next()
            } else {
                setAnswerState(index, AnswerFieldState.WRONG)
            }
        }
    }

    fun getValueFrom(index: Int): String {
        return if (reversedReview) answers[index].term.definition else answers[index].term.term
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnswerField(
            value = getValueFrom(0),
            answerFieldState = answerState[0],
            onClick = { checkField(0) }
        )

        AnswerField(
            value = getValueFrom(1),
            answerFieldState = answerState[1],
            onClick = { checkField(1) }
        )

        AnswerField(
            value = getValueFrom(2),
            answerFieldState = answerState[2],
            onClick = { checkField(2) }
        )

        AnswerField(
            value = getValueFrom(3),
            answerFieldState = answerState[3],
            onClick = { checkField(3) }
        )
    }
}