package com.playrix.gar.some_services.build.user_image.coordinator

import android.Manifest
import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.playrix.gar.some_services.build.user_image.image_permission.ImageCoordinatorRequestEnd
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import javax.inject.Inject

@ObfustringThis
class CoordinatorHandler@Inject constructor(
    @ActivityContext private val context: Context,
    private val imageCoordinatorRequestEnd: ImageCoordinatorRequestEnd,
) {
    val justPermission = Manifest.permission.CAMERA
    val hostInst get() = context as AppCompatActivity

    val launcherForPermission = hostInst.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { response ->
        onPermissionOk(response ?: false)
    }

    fun onPermissionOk(boolean: Boolean) {
        imageCoordinatorRequestEnd.processTheResult(boolean)
    }
}