package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.domain.util.ImageConverter
import com.kappdev.wordbook.feature_dictionary.domain.util.resize

class GetImageFromStorage(
    private val context: Context
) {

     operator fun invoke(uri: Uri) : String? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream, null, null)
            inputStream?.close()

            bitmap?.let {
                return ImageConverter().bitmapToString(bitmap.resize())
            }
            return null
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.image_load_error))
        }
     }
}