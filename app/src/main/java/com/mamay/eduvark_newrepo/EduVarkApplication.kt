package com.mamay.eduvark_newrepo

import android.app.Application
import com.mamay.eduvark_newrepo.ui.theme.EduResources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EduVarkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        EduResources.install(this)
    }
}
