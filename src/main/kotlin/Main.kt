package org.example

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions

//val cred = GoogleCredentials.from
val storage: Storage = StorageOptions.getDefaultInstance().service
//.newBuilder().setCredentials().build().service()

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val bucketName = "swetha-dev-anaml-io"
    val bucket = storage.get(bucketName)
        ?: error("Bucket $bucketName does not exist. You can create a new bucket with the command 'create <bucket>'. ")

    if (bucket.list().iterateAll().count() == 0) {
        println("Looks like your bucket is empty. You can upload blobs to your bucket with the 'upload' command.")
        return
    }

    println("Listing all blobs in bucket $bucketName: \n")
    for (blob in bucket.list().iterateAll()) {
        println("Name: ${blob.name} \t Content Type: ${blob.contentType} \t Size: ${blob.size}")
    }
}