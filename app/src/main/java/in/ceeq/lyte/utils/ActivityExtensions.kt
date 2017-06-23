package `in`.ceeq.lyte.utils

import `in`.ceeq.lyte.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

infix fun AppCompatActivity.start(extras: Bundle): Unit {
    val starter = Intent(this, LoginActivity::class.java)
    starter.putExtra("extras", extras)
    startActivity(starter)
}
