package `in`.ceeq.lyte.databinding

import android.databinding.ObservableField

data class LoginRequest(val mobile: ObservableField<String>,
                        val email: ObservableField<String>,
                        val password: ObservableField<String>)
