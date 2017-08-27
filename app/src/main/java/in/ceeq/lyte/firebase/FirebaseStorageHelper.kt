package `in`.ceeq.lyte.firebase

import android.arch.lifecycle.LifecycleObserver
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference


class FirebaseStorageHelper<T> : LifecycleObserver {

    private val mFirebaseStorage:
            FirebaseStorage by lazy { FirebaseStorage.getInstance() }

    private val mStorageReference:
            StorageReference by lazy { mFirebaseStorage.reference.child(mNode) }

    private lateinit var mNode: String

    init {
    }

    fun setNode(node: String) {
        mNode = node
    }

    fun push(uri: Uri) {
        val metadata = StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build()
        val storageReference = mStorageReference.child(uri.lastPathSegment)
        val uploadTask = storageReference.putFile(uri, metadata) // Add optional metadata
        var downloadUrl: Uri
        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
            println("Upload is $progress% done")
        }.addOnPausedListener {
            println("Upload is paused")
        }.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // Handle successful uploads on complete
            taskSnapshot.metadata?.downloadUrl
        }
    }
}
