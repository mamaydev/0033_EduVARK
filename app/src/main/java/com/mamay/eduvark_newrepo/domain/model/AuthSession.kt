package com.mamay.eduvark_newrepo.domain.model

data class AuthSession(
    val accessToken: String,
    val refreshToken: String,
    val user: UserProfile,
    val varkRequired: Boolean,
    val activeVark: VarkType?,
)
