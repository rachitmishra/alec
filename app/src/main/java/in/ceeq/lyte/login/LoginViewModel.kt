package `in`.ceeq.lyte.login


import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.databinding.ObservableField
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
typealias OnLogin = (LoginViewModel.LoginWith) -> Unit

class LoginViewModel(var context: Context, val onlogin: OnLogin) : ViewModel() {

    enum class LoginWith {
        MOBILE,
        EMAIL,
        ANON,
        GOOGLE
    }

    val mAuth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { FirebaseAuth.getInstance() }

    val showMobileLayout : ObservableField<Boolean> by lazy(LazyThreadSafetyMode.NONE) { ObservableField(false)}
    val showButtonLayout : ObservableField<Boolean> by lazy(LazyThreadSafetyMode.NONE) { ObservableField(true)}

    fun getGoogleSignInOptions() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("")
                .requestEmail()
                .build()
    }

    fun handleResultForGoogleLogin(intent: Intent) {
    }

    private fun handleLoginFailure() {

    }

    private fun handleLoginSuccess(currentUser: FirebaseUser?) {

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
    }

    fun onLogin(view: View) {

    }

    fun onEmailLogin(view: View) {

    }

    fun onGoogleLogin(view: View) {

    }

    fun onMobileLogin(view: View) {
        showButtonLayout.set(false)
        showMobileLayout.set(true)
    }
}

