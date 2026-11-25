package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.CaseStudy
import com.mamay.eduvark_newrepo.domain.model.SubmissionResult

interface CaseRepository {
    suspend fun getStudentCase(topicId: String): CaseStudy
    suspend fun submitCase(topicId: String, answerText: String): SubmissionResult
    suspend fun getLecturerCase(topicId: String): CaseStudy?
    suspend fun upsertCase(
        topicId: String,
        theory: String?,
        instructions: String,
        deadlineAt: String,
    ): CaseStudy
}
