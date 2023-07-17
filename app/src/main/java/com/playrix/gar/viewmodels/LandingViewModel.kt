package com.playrix.gar.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playrix.gar.data.api.link.LinkParts
import com.playrix.gar.data.constants.shared_preferences.MySharedPreferences
import com.playrix.gar.some_services.collectors.link.link.LinkCollection
import com.playrix.gar.some_services.online.MyFirebaseImpl
import com.playrix.gar.some_services.services.facebook.FacebookImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val myFirebaseImpl: MyFirebaseImpl,
    private val linkParts: LinkParts,
    private val myFacebookImpl: FacebookImpl,
    private val linkCollection: LinkCollection
) : ViewModel() {

    private val _appRunFlow = MutableSharedFlow<Boolean>()
    val appRunFlow = _appRunFlow.asSharedFlow()

    private val _savedStringFlow = MutableSharedFlow<String>(1)

    val sharedPreferencesStringFlow = _savedStringFlow.asSharedFlow()

    private val _baseStringFlow = MutableSharedFlow<String>()
    val baseStringFlow = _baseStringFlow.asSharedFlow()

    fun checkSavings(context: Context) {
        if (MySharedPreferences.isSomethingPresentInSp()) {
            makeCacheLaunch()

        } else {
            doFirebaseUpdates(context)
        }
    }

    private fun doFirebaseUpdates(context: Context) {
        myFirebaseImpl.updateParams {
            if (it.isSuccessful) {
                myFirebaseImpl.apply {
                    onFirebaseOk()

                    doFirebaseCheck(context)

                }
            } else {
                launchPlug()
            }
        }
    }

    private fun doFirebaseCheck(context: Context) {
        if (linkParts.stringEntry.runCheck()
        ) {
            createNewScope(context)
        } else {
            launchPlug()
        }
    }

    private fun makeCacheLaunch() {
        viewModelScope.launch {
            _savedStringFlow.emit(MySharedPreferences.providedSavedString)
        }
    }

    private fun launchPlug() {
        viewModelScope.launch {
            _appRunFlow.emit(true)
        }
    }

    private fun createNewScope(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            myFacebookImpl.initMyFb(linkParts.cid, linkParts.fClient)

            _baseStringFlow.emit(linkCollection.collectAll(context))
        }
    }

    private fun MyFirebaseImpl.onFirebaseOk() {
        linkParts.apply {
            stringEntry = getTString()

            val extractedCid = getIdString()
            if(extractedCid.runCheck()) {
                cid = extractedCid
            }

            val extractedFClient = getCtString()
            if(extractedFClient.runCheck()) {
                fClient = extractedFClient
            }

            val extractedFDec = getFDecString()
            if(extractedFDec.runCheck()) {
                fDec = extractedFDec
            }

            afEnable = getIsAppsFEnable()
        }
    }

    private fun String.runCheck(): Boolean {
        return this != null.toString() &&
                this.isNotEmpty() &&
                this.isNotBlank()
    }
}