package com.playrix.gar.view.activity_fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.playrix.gar.R
import com.playrix.gar.databinding.ActivityMyFragmentsBinding
import com.playrix.gar.some_services.services.downloader.DownloadingManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFragmentsActivity : AppCompatActivity() {
    private var _binding: ActivityMyFragmentsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var downloadingManager: DownloadingManager
    private lateinit var fragmentController: NavController

    private val mNavigation = MainNavigation()
    private val mListeners = MainListeners()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentController = MainNavigation().obtainFragmentController()

        mListeners.apply {
            binding.groupTouggleButton.addToggleGroupListener()
        }

        downloadingManager.initLauncher(this@MyFragmentsActivity)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    inner class MainListeners {
        fun MaterialButtonToggleGroup.addToggleGroupListener() {
            addOnButtonCheckedListener { _, checkedButtonId, isCheckedState ->
                if (isCheckedState) {
                    when (checkedButtonId) {
                        R.id.button_page -> mNavigation.navTo(R.id.navigation_page)
                        R.id.button_liked -> mNavigation.navTo(R.id.navigation_liked)
                        R.id.button_lookup -> mNavigation.navTo(R.id.navigation_lookup)
                        R.id.button_message_board -> mNavigation.navTo(R.id.navigation_message_board)
                        R.id.button_close_app ->
                            mNavigation.apply {
                                android.os.Process.myPid().killApplication()
                            }
                    }
                }
            }
        }
    }

    inner class MainNavigation {
        fun obtainFragmentController(): NavController {
            return (supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment)
                .navController
        }

        fun navTo(fragmentId: Int) {
            fragmentController.navigate(fragmentId)
        }

        fun Int.killApplication() {
            this@MyFragmentsActivity.finishAffinity()
            android.os.Process.killProcess(this)
        }
    }
}