package com.kappdev.wordbook.feature_dictionary.domain.use_cases

import android.content.Context
import android.content.Intent
import com.kappdev.wordbook.R
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase.Companion.DATABASE_NAME
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase.Companion.SHM
import com.kappdev.wordbook.feature_dictionary.data.data_source.DictionaryDatabase.Companion.WAL
import com.kappdev.wordbook.feature_dictionary.domain.util.ZipFilesProvider
import java.text.SimpleDateFormat
import java.util.*

class ExportDatabase(
    private val context: Context,
    private val db: DictionaryDatabase
) {

    operator fun invoke() {
        if (db.isOpen) db.close()
        val zipFileName = "WordBookDbBackup" + getDate() + ".zip"

        val dbFile = context.getDatabasePath(DATABASE_NAME)
        val db = dbFile.path
        val shm = dbFile.path + SHM
        val wal = dbFile.path + WAL
        val dbFilesPaths = arrayOf(db, shm, wal)

        val uri = ZipFilesProvider.prepareFilesToShareAsZippedFile(dbFilesPaths, zipFileName)
        val shareSheetTitle = context.getString(R.string.share_db_with_title)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = ZipFilesProvider.ZIP_FILE_MIME_TYPE
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

        val chooserIntent = Intent.createChooser(shareIntent, shareSheetTitle)
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooserIntent)
    }

    private fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
        val now = Date()
        return "_" + formatter.format(now)
    }
}