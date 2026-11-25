package com.mamay.eduvark_newrepo.data.repository

import com.mamay.eduvark_newrepo.core.network.EduVarkApi
import com.mamay.eduvark_newrepo.data.remote.ApiExecutor
import com.mamay.eduvark_newrepo.data.remote.dto.SaveFeedbackRequest
import com.mamay.eduvark_newrepo.domain.error.AppFailure
import com.mamay.eduvark_newrepo.domain.model.Feedback
import com.mamay.eduvark_newrepo.domain.repository.FeedbackRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackRepositoryImpl @Inject constructor(
    private val api: EduVarkApi,
    private val executor: ApiExecutor,
) : FeedbackRepository {

    override suspend fun getFeedback(topicId: String): Feedback {
        val response = executor.execute { api.getFeedback(topicId) }
        val data = response.data ?: throw AppFailure.Unknown
        return Feedback(value = data.value, updatedAt = data.updatedAt)
    }

    override suspend fun saveFeedback(topicId: String, value: Int): Feedback {
        val response = executor.execute {
            api.saveFeedback(topicId, SaveFeedbackRequest(value = value))
        }
        val data = response.data ?: throw AppFailure.Unknown
        return Feedback(value = data.value, updatedAt = data.updatedAt)
    }
}
