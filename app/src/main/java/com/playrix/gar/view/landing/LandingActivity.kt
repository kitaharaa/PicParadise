package com.playrix.gar.view.landing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.playrix.gar.data.my_enums.NotificationEnum
import com.playrix.gar.data.my_enums.e_intent.BaseIntents
import com.playrix.gar.databinding.ActivityOpeningBinding
import com.playrix.gar.some_services.pusher.NotificationAnalytic.changeLink
import com.playrix.gar.some_services.pusher.NotificationAnalytic.navigateWithCheckPush
import com.playrix.gar.some_services.pusher.NotificationAnalytic.signalValue
import com.playrix.gar.view.activity_fragment.MyFragmentsActivity
import com.playrix.gar.view.online_services.OnlineServicesActivity
import com.playrix.gar.viewmodels.LandingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {
    private val viewModel: LandingViewModel by viewModels()

    private var _binding: ActivityOpeningBinding? = null
    private val binding get() = _binding!!

    private val pushRequester = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        runViewModelCheck()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isTaskRoot &&
            intent?.hasCategory(Intent.CATEGORY_LAUNCHER) == true && intent?.action?.equals(Intent.ACTION_MAIN) == true
        ) {
            finish()
            return
        }

        _binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAppLogic()
    }

    private fun startAppLogic() {
        collectSharedFlow()
        runRequester()
    }

    private fun runRequester() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (runPermissionCheck()) {
                runViewModelCheck()

            } else pushRequester.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else runViewModelCheck()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun runPermissionCheck(launchString: String = Manifest.permission.POST_NOTIFICATIONS): Boolean {
        return checkSelfPermission(launchString) == PackageManager.PERMISSION_GRANTED
    }

    private fun runViewModelCheck() {
        viewModel.checkSavings(this)
    }

    private fun observeCache() {
        lifecycleScope.launch {
            viewModel.sharedPreferencesStringFlow.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest {
                val baseIntent = Intent(
                    this@LandingActivity,
                    OnlineServicesActivity::class.java
                )

                this@LandingActivity.navigateWithCheckPush(
                    baseIntent.fillCacheIntent(it), true
                ) {
                    finish()
                }
            }
        }
    }

    private fun collectSharedFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.appRunFlow.collectLatest {
                        val baseIntent =
                            Intent(this@LandingActivity, MyFragmentsActivity::class.java)
                        this@LandingActivity.navigateWithCheckPush(baseIntent)

                        finish()
                    }
                }

                launch {
                    viewModel.baseStringFlow.collectLatest {
                        val genIntent =
                            Intent(this@LandingActivity, OnlineServicesActivity::class.java)

                        this@LandingActivity.navigateWithCheckPush(genIntent.fillOnlineIntent(it)) {
                            finish()
                        }
                    }
                }
            }
        }

        observeCache()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}

private fun Intent.fillCacheIntent(it: String): Intent {
    signalValue = NotificationEnum.DIRECT.signValue
    putExtra(BaseIntents.URL_TO_LOAD.intentString, changeLink(it))

    return this
}


private fun Intent.fillOnlineIntent(it: String): Intent {
    signalValue = NotificationEnum.FIRST_OPEN.signValue
    putExtra(BaseIntents.URL_TO_LOAD.intentString, changeLink(it))

    return this
}