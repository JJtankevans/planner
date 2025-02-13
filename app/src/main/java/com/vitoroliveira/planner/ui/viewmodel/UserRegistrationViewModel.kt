package com.vitoroliveira.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vitoroliveira.planner.data.datasource.UserRegistrationLocalDataSource
import com.vitoroliveira.planner.data.di.MainServiceLocator

class UserRegistrationViewModel: ViewModel() {

    private val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    fun getIsUserRegistered(): Boolean {
        return userRegistrationLocalDataSource.getIsUserRegistered()
    }

    fun saveIsUserRegistered(isUserRegistered: Boolean) {
        return userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered = isUserRegistered)
    }
}