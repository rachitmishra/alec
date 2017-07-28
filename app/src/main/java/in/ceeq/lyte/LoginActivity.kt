package `in`.ceeq.lyte

import `in`.ceeq.lyte.databinding.ActivityLoginBinding
import `in`.ceeq.lyte.databinding.LoginViewModel
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityLoginBinding: ActivityLoginBinding = setContentView(this, R.layout.activity_login)
        activityLoginBinding.loginViewModel = LoginViewModel(this) {
            when(it) {
                LoginViewModel.LoginWith.MOBILE ->
                    supportFragmentManager.beginTransaction().replace(activityLoginBinding.container,
                            FragmentLoginMobile.getInstance())
            }
        }
    }
}
