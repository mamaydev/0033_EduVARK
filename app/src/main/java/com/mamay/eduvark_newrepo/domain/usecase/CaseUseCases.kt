package com.mamay.eduvark_newrepo.domain.usecase

import com.mamay.eduvark_newrepo.domain.model.CaseStudy
import com.mamay.eduvark_newrepo.domain.model.SubmissionResult
import com.mamay.eduvark_newrepo.domain.repository.CaseRepository
import javax.inject.Inject

class GetStudentCaseUseCase @Inject constructor(
    private val repository: CaseRepository,
) {
    suspend operator fun invoke(topicId: String): CaseStudy =
        repository.getStudentCase(topicId)
}

class SubmitCaseUseCase @Inject constructor(
    private val repository: CaseRepository,
) {
    suspend operator fun invoke(topicId: String, answerText: String): SubmissionResult =
        repository.submitCase(topicId, answerText)
}

class GetLecturerCaseUseCase @Inject constructor(
    private val repository: CaseRepository,
) {
    suspend operator fun invoke(topicId: String): CaseStudy? =
        repository.getLecturerCase(topicId)
}

class UpsertCaseUseCase @Inject constructor(
    private val repository: CaseRepository,
) {
    suspend operator fun invoke(
        topicId: String,
        theory: String?,
        instructions: String,
        deadlineAt: String,
    ): CaseStudy = repository.upsertCase(topicId, theory, instructions, deadlineAt)
}
