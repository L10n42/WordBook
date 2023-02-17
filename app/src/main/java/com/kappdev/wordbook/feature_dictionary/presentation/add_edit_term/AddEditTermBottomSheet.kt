package com.kappdev.wordbook.feature_dictionary.presentation.add_edit_term

sealed class AddEditTermBottomSheet {
    object SelectPhotoSource: AddEditTermBottomSheet()
    object PasteLink: AddEditTermBottomSheet()
}
