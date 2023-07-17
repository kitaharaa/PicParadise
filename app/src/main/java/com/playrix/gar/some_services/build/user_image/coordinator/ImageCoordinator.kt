package com.playrix.gar.some_services.build.user_image.coordinator

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class ImageCoordinator @Inject constructor(
    private val coordinatorHandler: CoordinatorHandler
) {
    fun initPermissionRequest() {
        with(coordinatorHandler) {
            if (justPermission.runCompatCheck()) {
                coordinatorHandler.onPermissionOk(true)
            } else launcherForPermission.launch(justPermission)
        }
    }

    private fun String.runCompatCheck() = ContextCompat.checkSelfPermission(
        coordinatorHandler.hostInst,
        this
    ) == PackageManager.PERMISSION_GRANTED
}