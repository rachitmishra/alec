package `in`.ceeq.lyte

import `in`.ceeq.Lyte.R
import `in`.ceeq.Lyte.databinding.ActivityLoginBinding
import `in`.ceeq.lyte.databinding.LoginViewModel
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewBinding = setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        loginViewBinding.loginViewModel = LoginViewModel(this)
        setSupportActionBar(loginViewBinding.toolbar)
    }

}
