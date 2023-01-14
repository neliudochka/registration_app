package com.example.rgr

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import android.content.Context
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Paths

@RequiresApi(Build.VERSION_CODES.O)
class Record(
    context: Context,
    private var fileName: String
): Serializable {

    private val path = context.getExternalFilesDir(null)
    private var file: File = initRecord()

    private fun initRecord(): File {
        val oldRecord= File("$path/$fileName")
        //if exists continue records in the old file
        if(oldRecord.exists()) return oldRecord
        return File(path, fileName)
    }

    fun getFile(): File {
        return file
    }

    fun deleteFile() {
        val oldFilePath = Paths.get("$path/$fileName")
        Files.deleteIfExists(oldFilePath)
    }
}