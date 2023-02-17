package com.kappdev.wordbook.feature_dictionary.domain.util

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class FileUtils {
    private companion object {
        private const val BUFFER = 2048
    }

    fun moveFile(inputPath: String, inputFileName: String, outputPath: String) {
        try {
            val dir = File(outputPath)
            if (!dir.exists()) dir.mkdirs()

            val inputStream = FileInputStream(inputPath + inputFileName)
            val out = FileOutputStream(outputPath + inputFileName)
            val buffer = ByteArray(BUFFER)

            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                out.write(buffer, 0, read)
            }
            inputStream.close()

            out.flush()
            out.close()

            File(inputPath + inputFileName).delete()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}