package com.playrix.gar.view.online_services

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.data.constants.shared_preferences.MySharedPreferences.providedSavedString
import com.playrix.gar.data.my_enums.e_intent.BaseIntents
import com.playrix.gar.databinding.ActivityOnlineServiceBinding
import com.playrix.gar.some_services.helpers.my_dispatcher.CustomCallbackListener
import com.playrix.gar.some_services.helpers.settings.OnlineSetupHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnlineServicesActivity : AppCompatActivity() {
    private var _binding: ActivityOnlineServiceBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var customCallbackListener: CustomCallbackListener

    @Inject
    lateinit var onlineSetupHelper: OnlineSetupHelper

    private val Intent?.urlTOLOADToLoad
        get() = this?.getStringExtra(BaseIntents.URL_TO_LOAD.intentString).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnlineServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        providedSavedString = intent.urlTOLOADToLoad

        manageCookies(true)

        startAppLogic()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.onlineService.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        binding.onlineService.saveState(outState)
    }

    override fun onPause() {
        manageCookies(false)
        super.onPause()
    }

    private fun startAppLogic() {
        initHelper()
        initCallback()
    }

    private fun initCallback() {
        customCallbackListener.apply {
            urlToLoad = intent.urlTOLOADToLoad
            binding.onlineService.attachListener()
        }
    }

    private fun initHelper() {
        onlineSetupHelper.apply {
            this@OnlineServicesActivity.fullyInitOnlineService(
                intent.urlTOLOADToLoad,
                binding.onlineService
            )
        }
    }

    private fun manageCookies(turnOn: Boolean) {
        when (turnOn) {
            true -> CookieManager.getInstance().apply {
                setAcceptCookie(true)
                setAcceptThirdPartyCookies(binding.onlineService, true)
            }

            else -> CookieManager.getInstance().flush()
        }
    }
}