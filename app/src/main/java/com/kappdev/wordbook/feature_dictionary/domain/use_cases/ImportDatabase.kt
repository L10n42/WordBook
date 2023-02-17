package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import android.net.Uri
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.domain.util.FileUtils
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase.Companion.DATABASE_NAME
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase.Companion.DATABASE_SHM
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase.Companion.DATABASE_WAL
import com.kappdev.wordbook.feature_dictionary.domain.repository.DictionaryRepository
import com.kappdev.wordbook.feature_dictionary.domain.util.GetFile
import com.kappdev.wordbook.feature_dictionary.domain.util.ZipConvertor
import java.io.*

class ImportDatabase(
    private val context: Context,
    private val repository: DictionaryRepository
) {
    private val dbFilesNames = arrayOf(DATABASE_NAME, DATABASE_SHM, DATABASE_WAL)
    private val cacheDirPath = context.cacheDir.path + File.separator
    private lateinit var dbZipFile: File
    private lateinit var dbFolderPath: String
    private lateinit var dbFilesPaths: Array<String>

    operator fun invoke(uri: Uri): String {

        dbZipFile = GetFile().from(context, uri)
        if (!dbZipFile.exists()) throw Exception(context.getString(R.string.error_could_not_find_file))

        if (!ZipConvertor().zipContainsAll(dbZipFile, dbFilesNames)) {
            deleteZipInCache()
            throw Exception(context.getString(R.string.error_wrong_file))
        }

        val dbFolder = context.getDatabasePath(DATABASE_NAME).parent
        if (dbFolder != null)
            dbFolderPath = dbFolder + File.separator
        else {
            deleteZipInCache()
            throw Exception(context.getString(R.string.error_something_went_wrong))
        }
        
        getDbFilesPaths()
        backupCurrentDb()
        ZipConvertor().unzip(dbZipFile, dbFolderPath)

        if (!validateDb()) {
            throw Exception(context.getString(R.string.error_could_not_import_db))
        }

        return context.getString(R.string.db_successfully_imported)
    }
    
    private fun getDbFilesPaths() {
        val dbFilePath = context.getDatabasePath(DATABASE_NAME).path
        val shmFilePath = dbFilePath + DictionaryDatabase.SHM
        val walFilePath = dbFilePath + DictionaryDatabase.WAL
        dbFilesPaths = arrayOf(dbFilePath, shmFilePath, walFilePath)
    }

    private fun backupCurrentDb() {
        dbFilesPaths.forEach { path ->
            FileUtils().moveFile(dbFolderPath, getFileName(path), cacheDirPath)
        }
    }

    private fun validateDb(): Boolean {
        return if (repository.getSetsCount() <= 0) {
            deleteDbFiles()
            restoreBackupDb()
            deleteZipInCache()
            false
        } else {
            deleteBackupDb()
            deleteZipInCache()
            true
        }
    }

    private fun deleteZipInCache() {
        if (dbZipFile.exists()) dbZipFile.delete()
    }
    
    private fun deleteDbFiles() {
        dbFilesPaths.forEach { path ->
            val file = File(path)
            if (file.exists()) file.delete()
        }
    }

    private fun deleteBackupDb() {
        dbFilesPaths.forEach { path ->
            val backupFile = File(cacheDirPath + getFileName(path))
            if (backupFile.exists()) backupFile.delete()
        }
    }

    private fun restoreBackupDb() {
        dbFilesPaths.forEach { path ->
            FileUtils().moveFile(cacheDirPath, getFileName(path), dbFolderPath)
        }
    }

    private fun getFileName(path: String): String {
        val file = File(path)
        return file.name
    }
}