package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.AuthSession
import com.mamay.eduvark_newrepo.domain.model.UserProfile
import com.mamay.eduvark_newrepo.domain.model.VarkType

interface AuthRepository {
    suspend fun login(loginId: String, password: String): AuthSession
    suspend fun logout()
    suspend fun me(): UserProfile
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmation: String,
    )
    suspend fun selectVark(vark: VarkType): VarkType
}
