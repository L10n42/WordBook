package com.kappdev.wordbook.feature_dictionary.domain.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.domain.model.DrawerItem

object DrawerItems {

    val DeleteForever = DrawerItem(
        icon = Icons.Default.DeleteForever,
        titleResId = R.string.drawer_remove,
        id = "delete forever"
    )

    val Remove = DrawerItem(
        icon = Icons.Default.Delete,
        titleResId = R.string.drawer_remove,
        id = "remove"
    )

    val Edit = DrawerItem(
        icon = Icons.Default.Edit,
        titleResId = R.string.drawer_edit,
        id = "edit"
    )

    val Close = DrawerItem(
        icon = Icons.Default.Close,
        titleResId = R.string.drawer_close,
        id = "close"
    )

    val FlashCards = DrawerItem(
        icon = Icons.Default.Style,
        titleResId = R.string.drawer_flashcards,
        id = "flashCards"
    )

    val MoveTo = DrawerItem(
        icon = Icons.Default.MoveToInbox,
        titleResId = R.string.drawer_move_to,
        id = "move_to"
    )

    val ShareSet = DrawerItem(
        icon = Icons.Default.Share,
        titleResId = R.string.share_set_title,
        id = "share_set"
    )

    val Tests = DrawerItem(
        icon = Icons.Default.FactCheck,
        titleResId = R.string.drawer_tests,
        id = "tests"
    )

    val Writing = DrawerItem(
        icon = Icons.Default.Draw,
        titleResId = R.string.drawer_writing,
        id = "writing"
    )

    // lists
    val setsMultiSelectBarItems = listOf(Tests, Writing, FlashCards, DeleteForever, Close)

    val popupSetMoreItems = listOf(Edit, ShareSet, FlashCards, Tests, Writing, Remove)

    val popupTermMoreItems = listOf(Edit, MoveTo, Remove)
}