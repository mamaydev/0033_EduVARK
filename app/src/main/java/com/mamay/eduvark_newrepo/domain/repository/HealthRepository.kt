package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.HealthStatus

interface HealthRepository {
    suspend fun getHealth(): HealthStatus
}
