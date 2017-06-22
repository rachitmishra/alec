package `in`.ceeq.alec.databinding


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(var context: Context) {

    val mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()
    }


    fun handleStart() {
        val currentUser = mAuth.currentUser
        if (null != currentUser) {
            handleLoginSuccess(currentUser)
        }
    }

    fun getGoogleSignInOptions() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("")
                .requestEmail()
                .build()
    }

    fun handleResultForGoogleLogin(intent: Intent) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
        if (result.isSuccess) {
            // Google Sign In was successful, authenticate with Firebase
            val account = result.signInAccount
            firebaseAuthWithGoogle(account)
        } else {

        }
    }

    private fun handleLoginFailure() {

    }

    private fun handleLoginSuccess(currentUser: FirebaseUser?) {

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        acct?.let {
            Log.d(TAG, "firebaseAuthWithGoogle:" + it.id)
            val credential = GoogleAuthProvider.getCredential(it.idToken, null)
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener({
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            val user = mAuth.currentUser
                            handleLoginSuccess(user)
                        } else {
                            handleLoginFailure()
                        }
                    })
        }

    }

    fun onLogin(view: View) {

    }

    fun onEmailLogin(view: View) {

    }

    fun onGoogleLogin(view: View) {

    }

    fun onMobileLogin(view: View) {

    }
}

