package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import android.net.Uri
import com.kappdev.wordbook.R
import com.google.gson.Gson
import com.kappdev.wordbook.feature_dictionary.domain.model.SetJSON
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import java.io.IOException
import java.nio.charset.Charset

class ImportSet(
    private val context: Context,
    private val repository: DictionaryRepository
) {

    suspend operator fun invoke(uri: Uri): String {
        val jsonString = loadJSONFromUri(uri)
        if (jsonString.isEmpty()) throw Exception(context.getString(R.string.error_cannot_read_the_file))

        try {
            val data = Gson().fromJson(jsonString, SetJSON::class.java)
            writeDataToDB(data)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.error_wrong_file))
        }

        return context.getString(R.string.set_successfully_imported)
    }

    private suspend fun writeDataToDB(data: SetJSON) {
        deleteSetOnExists(data.setId)
        repository.addSet(data.getSet())
        data.terms.forEach { term ->
            repository.addTerm(term)
        }
    }

    private suspend fun deleteSetOnExists(setId: String) {
        val set = repository.getSetById(setId)
        if (set != null) {
            repository.removeSet(set)
            repository.removeAllTermsBySet(set.setId)
        }
    }

    private fun loadJSONFromUri(uri: Uri): String {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?: return ""
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()

            return String(buffer, charset)
        }
        catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
    }
}