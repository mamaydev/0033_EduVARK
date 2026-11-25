package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.Feedback

interface FeedbackRepository {
    suspend fun getFeedback(topicId: String): Feedback
    suspend fun saveFeedback(topicId: String, value: Int): Feedback
}
