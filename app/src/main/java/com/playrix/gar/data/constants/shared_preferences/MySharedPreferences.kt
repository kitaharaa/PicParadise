package com.playrix.gar.data.constants.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import com.playrix.gar.data.my_enums.e_shared.SharedPreferencesEnum
import com.playrix.gar.data.shared.MyLinkPreferencesRepo

object MySharedPreferences : MyLinkPreferencesRepo {
    private var sp: SharedPreferences? = null
    private val spFieldName = SharedPreferencesEnum.FIELD.sharedString

    var providedSavedString: String
        get() {
            return getSavedSp()
        }
        set(value) {
            performSpSavings(value)
        }

    override fun isSomethingPresentInSp(): Boolean = providedSavedString != ""

    override fun initSp(context: Context) {
        sp =
            context.getSharedPreferences(
                SharedPreferencesEnum.NAME.sharedString,
                Context.MODE_PRIVATE
            )
    }

    private fun performSpSavings(value: String) {
        sp?.edit()?.apply {
            putString(spFieldName, value)?.apply()
        }
    }

    private fun getSavedSp(): String {
        val defaultString = ""
        val string = sp?.getString(spFieldName, defaultString)

        return string.toString()
    }
}