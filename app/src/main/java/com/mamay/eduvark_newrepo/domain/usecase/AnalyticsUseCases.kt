package com.mamay.eduvark_newrepo.domain.usecase

import com.mamay.eduvark_newrepo.domain.model.TopicAnalytics
import com.mamay.eduvark_newrepo.domain.model.VarkType
import com.mamay.eduvark_newrepo.domain.repository.AnalyticsRepository
import javax.inject.Inject

class GetTopicAnalyticsUseCase @Inject constructor(
    private val repository: AnalyticsRepository,
) {
    suspend operator fun invoke(
        topicId: String,
        category: VarkType? = null,
        studentId: String? = null,
    ): TopicAnalytics = repository.getTopicAnalytics(topicId, category, studentId)
}
