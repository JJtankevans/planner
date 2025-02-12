package com.vitoroliveira.planner.data.di

import android.app.Application
import com.vitoroliveira.planner.data.datasource.UserRegistrationLocalDataSource
import com.vitoroliveira.planner.data.datasource.UserRegistrationLocalDataSourceImpl

object MainServiceLocator {
    private var _application: Application? = null
    private val application: Application get() = _application!!

    val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        UserRegistrationLocalDataSourceImpl(applicationContext = application.applicationContext)
    }

    fun initialize(application: Application) {
        _application = application
    }

    fun clear() {
        _application = null
    }
}