package com.kappdev.wordbook.feature_dictionary.domain.util

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.concurrent.thread

class ZipFilesProvider : ContentProvider() {
    override fun onCreate() = true
    override fun getType(uri: Uri) = ZIP_FILE_MIME_TYPE
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun attachInfo(context: Context, info: ProviderInfo) {
        super.attachInfo(context, info)
        authority = info.authority
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val filesPathsToCompress = getFilesPathsToCompress(uri)
        filesPathsToCompress.forEach { if (!it.exists()) throw FileNotFoundException(it.absolutePath) }
        val pipes = ParcelFileDescriptor.createReliablePipe()
        thread {
            val writeFd = pipes[1]
            try {
                ZipOutputStream(FileOutputStream(writeFd.fileDescriptor)).use { zipStream: ZipOutputStream ->
                    filesPathsToCompress.forEach {
                        zipStream.putNextEntry(ZipEntry(it.name))
                        FileInputStream(it).copyTo(zipStream)
                        zipStream.closeEntry()
                    }
                    zipStream.close()
                    writeFd.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                try {
                    writeFd.closeWithError(e.message)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return pipes[0]
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val filePathsToCompress = getFilesPathsToCompress(uri)
        val fileToCompressInfo = uri.encodedPath!!.substringAfter("/")
        val columnNames = projection ?: arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE)
        val ret = MatrixCursor(columnNames)
        val values = arrayOfNulls<Any>(columnNames.size)
        for (i in columnNames.indices) {
            when (columnNames[i]) {
                MediaStore.MediaColumns.DISPLAY_NAME -> values[i] = fileToCompressInfo
                MediaStore.MediaColumns.SIZE -> {
                    var totalFilesSize = 0L
                    filePathsToCompress.forEach { totalFilesSize += it.length() }
                    values[i]
                }
            }
        }
        ret.addRow(values)
        return ret
    }

    companion object {
        lateinit var authority: String
        const val ZIP_FILE_MIME_TYPE = "application/zip"

        private fun getFilesPathsToCompress(uri: Uri): HashSet<File> {
            val filePathToCompress = HashSet<File> (uri.queryParameterNames.size)
            uri.queryParameterNames.forEach {
                val path = uri.getQueryParameters(it)[0]
                filePathToCompress.add(File(path))
            }
            return filePathToCompress
        }

        fun prepareFilesToShareAsZippedFile(filesToCompress: Array<String>, zipFileName: String): Uri {
            val builder = Uri.Builder().scheme("content").authority(authority).encodedPath(zipFileName)
            for ((index, filePath) in filesToCompress.withIndex()) {
                builder.appendQueryParameter(index.toString(), filePath)
            }
            return builder.build()
        }
    }
}