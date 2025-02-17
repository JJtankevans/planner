package com.vitoroliveira.planner.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitoroliveira.planner.data.datasource.UserRegistrationLocalDataSource
import com.vitoroliveira.planner.data.di.MainServiceLocator
import com.vitoroliveira.planner.data.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserRegistrationViewModel: ViewModel() {

    private val userRegistrationLocalDataSource: UserRegistrationLocalDataSource by lazy {
        MainServiceLocator.userRegistrationLocalDataSource
    }

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    private val _isProfileValid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isProfileValid: StateFlow<Boolean> = _isProfileValid.asStateFlow()

    init {
        viewModelScope.launch {
            userRegistrationLocalDataSource.profile.collect { profile ->
                _profile.value = profile
            }
        }
    }

    fun getIsUserRegistered(): Boolean {
        return userRegistrationLocalDataSource.getIsUserRegistered()
    }

    fun saveIsUserRegistered(isUserRegistered: Boolean) {
        return userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered = isUserRegistered)
    }

    fun updateProfile(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        image: String? = null
    ) {
        //Se todos os campos forem nulos nao faz nada
        if(name == null && email == null && phone == null && image == null) return

        /*
        * Atualiza somente o campo em que ocorreu a mudança
        * em seguida atualiza o _isProfileValid com base na funcao
        * isValid do  ProfileModel */
        _profile.update { currentProfile ->
            val updatedProfile = currentProfile.copy(
                name = name ?: currentProfile.name,
                email = email ?: currentProfile.email,
                phone = phone ?: currentProfile.phone,
                image = image ?: currentProfile.image
            )

            _isProfileValid.update { updatedProfile.isValid() }

            updatedProfile
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            userRegistrationLocalDataSource.saveProfile(profile = profile.value)
            userRegistrationLocalDataSource.saveIsUserRegistered(isUserRegistered = true)
        }
    }
}