package `in`.ceeq.lyte.login

import `in`.ceeq.lyte.R
import `in`.ceeq.lyte.databinding.ActivityLoginBinding
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityLoginBinding: ActivityLoginBinding = setContentView(this, R.layout.activity_login)
        activityLoginBinding.loginViewModel = LoginViewModel(this) {
        }
    }

    companion object {

        infix fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
}
