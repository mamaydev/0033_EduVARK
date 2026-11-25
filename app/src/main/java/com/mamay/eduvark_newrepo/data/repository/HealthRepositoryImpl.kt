package com.mamay.eduvark_newrepo.data.repository

import com.mamay.eduvark_newrepo.core.network.EduVarkApi
import com.mamay.eduvark_newrepo.domain.model.HealthStatus
import com.mamay.eduvark_newrepo.domain.repository.HealthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepositoryImpl @Inject constructor(
    private val api: EduVarkApi,
) : HealthRepository {
    override suspend fun getHealth(): HealthStatus {
        val response = api.health()
        val data = response.data
            ?: error(response.error?.message ?: "Respons health tidak berisi data.")
        return HealthStatus(
            status = data.status,
            service = data.service,
            timestamp = data.timestamp,
        )
    }
}
