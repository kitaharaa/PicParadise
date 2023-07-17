package com.playrix.gar.some_services.online

import com.google.android.gms.tasks.OnCompleteListener

interface MyFirebaseUpdatingRepo {
    fun updateParams(paramsUpdatingListener: OnCompleteListener<Boolean>)
}