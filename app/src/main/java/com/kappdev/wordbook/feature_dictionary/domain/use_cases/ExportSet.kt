package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.kappdev.wordbook.BuildConfig.APPLICATION_ID
import com.kappdev.wordbook.R
import com.google.gson.Gson
import com.kappdev.wordbook.feature_dictionary.domain.model.SetJSON
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class ExportSet(
    private val context: Context,
    private val repository: DictionaryRepository
) {
    private val cacheDirPath = context.cacheDir.path + File.separator
    private val jsonType = ".json"

    operator fun invoke(setId: String) {
        val set = repository.getSetById(setId) ?: return
        val terms = repository.getAllTermsBySetIdList(setId)
        if (terms.isEmpty()) return

        val setJson = SetJSON.from(set, terms)
        val setName = set.name.trim().replace(" ", "_")
        val fileName = "WordBook_set_$setName$jsonType"
        val path = cacheDirPath.plus(fileName)

        writeJsonFile(path = path, data = setJson)
        share(path)
    }

    private fun share(path: String) {
        val jsonFile = File(path)
        if (!jsonFile.exists()) return

        val jsonURI = FileProvider.getUriForFile(context, APPLICATION_ID + ".provider", jsonFile)
        val shareSheetTitle = context.getString(R.string.share_set_with_title)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.type = "application/json"
        shareIntent.putExtra(Intent.EXTRA_STREAM, jsonURI)

        val chooserIntent = Intent.createChooser(shareIntent, shareSheetTitle)
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

    private fun writeJsonFile(path: String, data: SetJSON) {
        deleteIfExists(path)
        try {
            PrintWriter(FileWriter(path)).use {
                val gson = Gson()
                val jsonString = gson.toJson(data)
                it.write(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
    }

    private fun deleteIfExists(path: String) {
        val file = File(path)
        if (file.exists()) file.delete()
    }
}