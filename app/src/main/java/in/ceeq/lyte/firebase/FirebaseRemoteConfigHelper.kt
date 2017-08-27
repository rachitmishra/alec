package `in`.ceeq.lyte.firebase

import `in`.ceeq.lyte.utils.LogUtils
import android.arch.lifecycle.LifecycleObserver
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class FirebaseRemoteConfigHelper<T> : LifecycleObserver {

    companion object {
        val DEFAULT_MESSAGE_LENGTH = 200
        val KEY_MESSAGE_LENGTH = "lyte_message_length"
    }

    private val mFirebaseRemoteConfig:
            FirebaseRemoteConfig by lazy { FirebaseRemoteConfig.getInstance() }

    init {
        val firebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build()
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings)

        val defaultMap = mutableMapOf<String, Any>()
        defaultMap.put(KEY_MESSAGE_LENGTH, DEFAULT_MESSAGE_LENGTH)
        mFirebaseRemoteConfig.setDefaults(defaultMap)
    }


    fun fetchConfig() {
        var cacheExpireTimeInterval = 3600L

        if (mFirebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpireTimeInterval = 0
        }

        mFirebaseRemoteConfig.fetch(cacheExpireTimeInterval)
                .addOnSuccessListener {
                    mFirebaseRemoteConfig.activateFetched()
                    applyMessageLength()

                }.addOnFailureListener {

        }
    }

    private fun applyMessageLength() {
        val messageLength = mFirebaseRemoteConfig.getLong(KEY_MESSAGE_LENGTH)
        LogUtils.LOG("Message length: $messageLength")
    }

}
