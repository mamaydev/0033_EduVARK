package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.AdminAccountStatus
import com.mamay.eduvark_newrepo.domain.model.AdminUser
import com.mamay.eduvark_newrepo.domain.model.AdminUserPage
import com.mamay.eduvark_newrepo.domain.model.EnrollmentResult
import com.mamay.eduvark_newrepo.domain.model.UserRole

interface AdminRepository {
    suspend fun listUsers(
        query: String?,
        role: UserRole?,
        status: AdminAccountStatus?,
        page: Int,
        pageSize: Int,
    ): AdminUserPage

    suspend fun getUser(userId: String): AdminUser

    suspend fun createUser(
        loginId: String?,
        fullName: String,
        role: UserRole,
        temporaryPassword: String,
    ): AdminUser

    suspend fun updateUser(
        userId: String,
        fullName: String?,
        role: UserRole?,
        loginId: String? = null,
    ): AdminUser

    suspend fun resetPassword(userId: String, temporaryPassword: String)

    suspend fun deactivateUser(userId: String)

    suspend fun updateEnrollment(enrollmentId: String, active: Boolean): EnrollmentResult
}
