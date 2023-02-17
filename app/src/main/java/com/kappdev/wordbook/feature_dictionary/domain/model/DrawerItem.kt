package com.kappdev.wordbook.feature_dictionary.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val icon: ImageVector,
    val titleResId: Int,
    val id: String
)
