package com.vitoroliveira.planner.data.datasource

import com.vitoroliveira.planner.data.model.Profile
import kotlinx.coroutines.flow.Flow

interface UserRegistrationLocalDataSource {
    val profile: Flow<Profile>

    fun getIsUserRegistered(): Boolean

    fun saveIsUserRegistered(isUserRegistered: Boolean)

    suspend fun saveProfile(profile: Profile)
}