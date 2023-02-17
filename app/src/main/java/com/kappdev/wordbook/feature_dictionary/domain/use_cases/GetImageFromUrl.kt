package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.domain.util.ImageConverter
import com.kappdev.wordbook.feature_dictionary.domain.util.resize

class GetImageFromUrl(
    private val context: Context
) {
    suspend operator fun invoke(url: String) : String? {
        try {
            val loading = ImageLoader(context)
            val request = ImageRequest.Builder(context).data(url).build()
            val result = (loading.execute(request) as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap

            return ImageConverter().bitmapToString(bitmap.resize())
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.image_load_error))
        }
    }
}