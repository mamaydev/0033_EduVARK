package com.mamay.eduvark_newrepo.domain.usecase

import com.mamay.eduvark_newrepo.domain.model.HealthStatus
import com.mamay.eduvark_newrepo.domain.repository.HealthRepository
import javax.inject.Inject

class GetHealthUseCase @Inject constructor(
    private val healthRepository: HealthRepository,
) {
    suspend operator fun invoke(): HealthStatus = healthRepository.getHealth()
}
