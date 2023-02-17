package com.kappdev.wordbook.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class LazyColumnScrollDirection {
    TOP, BOTTOM
}

@Composable
fun LazyColumnWithScrollIndicator(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    onScroll: (LazyColumnScrollDirection) -> Unit = {},
    content: LazyListScope.() -> Unit
) {
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                when {
                    delta < 0 ->  onScroll(LazyColumnScrollDirection.BOTTOM)
                    delta > 0 ->  onScroll(LazyColumnScrollDirection.TOP)
                }
                return Offset.Zero
            }
        }
    }

    LazyColumn(
        modifier = modifier.nestedScroll(nestedScrollConnection),
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        content = content
    )
}

@Composable
fun Modifier.verticalScrollBar(
    state: LazyListState,
    width: Dp = 1.dp,
    color: Color = Color.Black
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(duration)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollBar = state.isScrollInProgress || alpha > 0f

        if (needDrawScrollBar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollBarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollBarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), scrollBarOffsetY),
                size = Size(width.toPx(), scrollBarHeight),
                alpha = alpha
            )
        }
    }
}

@Composable
fun Modifier.simpleHorizontalScrollbar(
    state: LazyListState,
    height: Float = 12f,
    backgroundColor: Color = Color.DarkGray,
    color: Color = Color.LightGray
): Modifier {

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        if (firstVisibleElementIndex != null) {

            val scrollableItems = state.layoutInfo.totalItemsCount - state.layoutInfo.visibleItemsInfo.size
            val scrollBarWidth = this.size.width / scrollableItems
            var offsetX = ((this.size.width - scrollBarWidth) * firstVisibleElementIndex) / scrollableItems

            drawRect(
                color = backgroundColor,
                topLeft = Offset(x = 0f, y = this.size.height),
                size = Size(this.size.width, height),
                alpha = 1f
            )

            drawRect(
                color = color,
                topLeft = Offset(x = offsetX, y = this.size.height),
                size = Size(scrollBarWidth, height),
                alpha = 1f
            )
        }
    }
}

@Composable
fun Modifier.simpleVerticalScrollBar(
    state: LazyListState,
    width: Dp = 4.dp,
    backgroundColor: Color = Color.DarkGray,
    color: Color = Color.LightGray
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500
    var targetHeight by remember {
        mutableStateOf(0f)
    }
    var targetOffset by remember {
        mutableStateOf(0f)
    }

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(duration)
    )
    val height by animateFloatAsState(
        targetValue = targetHeight,
        animationSpec = tween(300)
    )
    val anOffsetY by animateFloatAsState(
        targetValue = targetOffset,
        animationSpec = tween(300)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        if (firstVisibleElementIndex != null) {

            val scrollableItems = state.layoutInfo.totalItemsCount - state.layoutInfo.visibleItemsInfo.size
            val scrollBarHeight = this.size.height / scrollableItems
            targetHeight = scrollBarHeight
            val offsetY = ((this.size.height - scrollBarHeight) * firstVisibleElementIndex) / scrollableItems
            targetOffset = offsetY

            drawRect(
                color = backgroundColor,
                topLeft = Offset(x = this.size.width - width.toPx(), y = 0f),
                size = Size(width.toPx(), this.size.height),
                alpha = alpha
            )

            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(16f, 16f),
                topLeft = Offset(x = this.size.width - width.toPx(), y = anOffsetY),
                size = Size(width.toPx(), height),
                alpha = alpha
            )
        }
    }
}