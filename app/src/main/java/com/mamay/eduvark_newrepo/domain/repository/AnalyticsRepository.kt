package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.TopicAnalytics
import com.mamay.eduvark_newrepo.domain.model.VarkType

interface AnalyticsRepository {
    suspend fun getTopicAnalytics(
        topicId: String,
        category: VarkType?,
        studentId: String?,
    ): TopicAnalytics
}
