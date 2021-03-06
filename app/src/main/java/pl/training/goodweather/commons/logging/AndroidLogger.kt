package pl.training.goodweather.commons.logging

import android.util.Log

class AndroidLogger : Logger {

    private val tag = AndroidLogger::class.java.name

    override fun log(text: String) {
        Log.d(tag, text)
    }

}