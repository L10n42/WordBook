package com.kappdev.wordbook.feature_dictionary.presentation.writing.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kappdev.wordbook.R
import com.kappdev.wordbook.core.presentation.learn_components.LearnTopBar
import com.kappdev.wordbook.core.presentation.navigation.Screen
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components.BackCard
import com.kappdev.wordbook.feature_dictionary.presentation.flashcards.components.FinalCard
import com.kappdev.wordbook.feature_dictionary.presentation.writing.WritingOptionsViewModel
import com.kappdev.wordbook.feature_dictionary.presentation.writing.WritingViewModel
import com.kappdev.wordbook.ui.theme.Green500
import com.kappdev.wordbook.ui.theme.Red500
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun WritingScreen(
    navController: NavHostController,
    viewModel: WritingViewModel = hiltViewModel(),
    optionsViewModel: WritingOptionsViewModel = hiltViewModel()
) {
    val progress = viewModel.progress.value
    val answered = viewModel.answered.value
    val cards = viewModel.unstudiedCards
    val currentAnswerValue = viewModel.currentAnswer.value
    val finished = progress.unstudied == 0 && progress.studied != 0

    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val done: () -> Unit = {
        viewModel.answer()
        focusManager.clearFocus()
    }

    LaunchedEffect(key1 = true) {
        val setsIds = navController.previousBackStackEntry?.savedStateHandle?.get<List<String>>("setsIds")
        if (setsIds != null && setsIds.isNotEmpty())
            viewModel.launch(setsIds)
        else navController.popBackStack()
    }

    ModalBottomSheetLayout(
        sheetState =  bottomSheetState,
        scrimColor = MaterialTheme.colors.onSurface.copy(alpha = 0.16f),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {
            WritingOptionsBottomSheet(optionsViewModel)
        }
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                LearnTopBar(
                    progress = progress,
                    onCloseClick = {
                        navController.navigate(Screen.Sets.route) { popUpTo(0) }
                    },
                    onSettingClick = {
                        scope.launch { bottomSheetState.show() }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                cards.take(1).forEach { card ->

                    if (!viewModel.isCurrentCard(card)) {
                        viewModel.writeCurrentCard(card)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        BackCard(
                            item = card,
                            isExampleVisible = optionsViewModel.isExamplesVisible.value,
                            isImageVisible = optionsViewModel.isImageVisible.value,
                            imageHeight = 100.dp,
                            definitionFontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(all = 16.dp)
                                .background(
                                    color = MaterialTheme.colors.surface,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(all = 16.dp)
                        )

                        if (answered) CorrectAnswerText(value = card.term.term)
                        Spacer(Modifier.height(16.dp))

                        AnswerField(
                            value = currentAnswerValue,
                            enabled = !answered,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        scope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            onDone = done,
                            onTextChanged = { newValue ->
                                viewModel.setNewAnswerValue(newValue)
                            }
                        )

                        Spacer(Modifier.height(32.dp))

                        if (answered) {
                            WritingScreenNextButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .bringIntoViewRequester(bringIntoViewRequester),
                                color = if (viewModel.isAnswerCorrect()) Green500 else Red500
                            ) { viewModel.next() }
                        } else {
                            WritingScreenActionButtons(
                                modifier = Modifier.fillMaxWidth().bringIntoViewRequester(bringIntoViewRequester),
                                showAnswer = done,
                                submit = done
                            )
                        }
                    }
                }

                if (finished) {
                    FinalCard(
                        Modifier
                            .fillMaxSize()
                            .padding(all = 16.dp)
                    ) { viewModel.studyAgain() }
                }
            }
        }
    }
}

@Composable
private fun AnswerField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean,
    onDone: () -> Unit,
    onTextChanged: (newValue: String) -> Unit
) {
    TextField(
        value = value,
        enabled = enabled,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            unfocusedLabelColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.onBackground,
            backgroundColor = MaterialTheme.colors.surface,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = MaterialTheme.colors.surface
        ),
        onValueChange = { newText ->
            onTextChanged(newText)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.label_enter_correct_answer_here),
                color = MaterialTheme.colors.onBackground
            )
        },
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        modifier = modifier.then( Modifier.clip(shape = RoundedCornerShape(8.dp)) )
    )
}

@Composable
private fun CorrectAnswerText(value: String) {
    Text(
        text = value,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}