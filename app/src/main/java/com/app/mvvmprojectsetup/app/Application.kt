package com.app.mvvmprojectsetup.app

import android.app.Application
import com.creatorstool.shaketrace.ShakeTrace
import dagger.hilt.android.HiltAndroidApp

/**
 * @Created by akash on 10/20/2023.
 * Know more about author on <a href="https://akash.cloudemy.in">...</a>
 */
@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        ShakeTrace.init(this)
    }
}