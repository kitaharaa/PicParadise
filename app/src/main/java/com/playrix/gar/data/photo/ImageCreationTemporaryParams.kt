package com.playrix.gar.data.photo

import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import java.io.File

data class ImageCreationTemporaryParams(
    var mFileName: String? = null,
    var mImageIntents: Intent? = null,
    var myCreatedImageFile: File? = null,
    var pChromeCallback: ValueCallback<Array<Uri>>? = null
)
