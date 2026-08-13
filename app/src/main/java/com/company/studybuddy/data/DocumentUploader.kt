package com.company.studybuddy.data

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.util.UUID

object DocumentUploader {

    suspend fun uploadFile(uri: Uri, fileName: String): String {
        val storageRef = Firebase.storage.reference

        val uniqueFileName = "uploads/${UUID.randomUUID()}_$fileName"
        val fileRef = storageRef.child(uniqueFileName)

        fileRef.putFile(uri).await()

        val bucket = fileRef.bucket
        val path = fileRef.path

        val cleanPath = path.removePrefix("/")

        return "gs://$bucket/$cleanPath"
    }
}