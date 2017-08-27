package `in`.ceeq.lyte.firebase

import android.arch.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthHelper : LiveData<Boolean>() {

    init {
        value = false
    }

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
