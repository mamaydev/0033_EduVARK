package com.mamay.eduvark_newrepo.domain.repository

import com.mamay.eduvark_newrepo.domain.model.AssessmentResult
import com.mamay.eduvark_newrepo.domain.model.CaseSubmissionSummary
import com.mamay.eduvark_newrepo.domain.model.SubmissionDetail

interface AssessmentRepository {
    suspend fun listCaseSubmissions(caseId: String): List<CaseSubmissionSummary>
    suspend fun getSubmission(submissionId: String): SubmissionDetail
    suspend fun createAssessment(
        submissionId: String,
        score: Int,
        comment: String?,
    ): AssessmentResult
}
