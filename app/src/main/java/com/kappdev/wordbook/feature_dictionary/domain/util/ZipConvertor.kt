package com.kappdev.wordbook.feature_dictionary.domain.util

import okio.IOException
import java.io.*
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

class ZipConvertor {
    private companion object {
        private const val BUFFER = 2048
    }

    fun zipContainsAll(zipFile: File, filesNames: Array<String>): Boolean {
        filesNames.forEach { fileName ->
            if (!zipContains(zipFile, fileName)) return false
        }
        return true
    }

    fun zipContains(zipFile: File, fileName: String): Boolean {
        try {
            ZipFile(zipFile).use { zip ->
                zip.entries().asSequence().forEach { entry ->
                    val name = entry.name
                    if (name == fileName) return true
                }
            }
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun unzip(zipFile: File, destDirectory: String) {
        createDirIfDoesNotExists(destDirectory)
        try {
            ZipFile(zipFile).use { zip ->

                zip.entries().asSequence().forEach { entry ->

                    zip.getInputStream(entry).use { input ->
                        val filePath = destDirectory + File.separator + entry.name
                        if (!entry.isDirectory) {
                            extractFile(input, filePath)
                        } else {
                            val dir = File(filePath)
                            dir.mkdir()
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createDirIfDoesNotExists(dir: String) {
        File(dir).run {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    private fun extractFile(inputStream: InputStream, destFilePath: String) {
        try {
            val bos = BufferedOutputStream(FileOutputStream(destFilePath))
            val bytesIn = ByteArray(BUFFER)
            var read: Int
            while (inputStream.read(bytesIn).also { read = it } != -1) {
                bos.write(bytesIn, 0, read)
            }
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}