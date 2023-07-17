package com.playrix.gar.some_services.online

interface MyFirebaseParamsRepo {
    fun getIsAppsFEnable(): Boolean
    fun getTString(): String
    fun getIdString(): String
    fun getCtString(): String
    fun getFDecString(): String
}