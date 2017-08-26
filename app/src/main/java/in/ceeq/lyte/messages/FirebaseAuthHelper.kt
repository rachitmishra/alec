package `in`.ceeq.lyte.messages

import android.arch.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthHelper : LiveData<Boolean>() {

    private val mFirebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val mFirebaseAuthStateListener = FirebaseAuth.AuthStateListener {
        value = it.currentUser != null
    }

    override fun onActive() {
        mFirebaseAuth.addAuthStateListener { mFirebaseAuthStateListener }
    }

    override fun onInactive() {
        mFirebaseAuth.addAuthStateListener { mFirebaseAuthStateListener }
    }
}
