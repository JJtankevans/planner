package com.vitoroliveira.planner

import android.app.Application
import com.vitoroliveira.planner.data.di.MainServiceLocator

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MainServiceLocator.initialize(application = this)
    }
}