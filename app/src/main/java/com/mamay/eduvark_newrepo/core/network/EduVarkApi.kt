package com.mamay.eduvark_newrepo.core.network

import com.mamay.eduvark_newrepo.data.remote.dto.AdminUserListResponse
import com.mamay.eduvark_newrepo.data.remote.dto.AdminUserResponse
import com.mamay.eduvark_newrepo.data.remote.dto.AssessmentResponse
import com.mamay.eduvark_newrepo.data.remote.dto.CaseResponse
import com.mamay.eduvark_newrepo.data.remote.dto.CaseSubmissionListResponse
import com.mamay.eduvark_newrepo.data.remote.dto.ChangePasswordRequest
import com.mamay.eduvark_newrepo.data.remote.dto.CreateAdminUserRequest
import com.mamay.eduvark_newrepo.data.remote.dto.CreateAssessmentRequest
import com.mamay.eduvark_newrepo.data.remote.dto.CreateTopicRequest
import com.mamay.eduvark_newrepo.data.remote.dto.EnrollmentResponse
import com.mamay.eduvark_newrepo.data.remote.dto.EnrollmentStatusRequest
import com.mamay.eduvark_newrepo.data.remote.dto.FeedbackResponse
import com.mamay.eduvark_newrepo.data.remote.dto.HealthResponse
import com.mamay.eduvark_newrepo.data.remote.dto.LinkMaterialRequest
import com.mamay.eduvark_newrepo.data.remote.dto.LoginRequest
import com.mamay.eduvark_newrepo.data.remote.dto.LoginResponse
import com.mamay.eduvark_newrepo.data.remote.dto.MaterialLinkTargetResponse
import com.mamay.eduvark_newrepo.data.remote.dto.MaterialDeletionResponse
import com.mamay.eduvark_newrepo.data.remote.dto.MaterialFileReplacementResponse
import com.mamay.eduvark_newrepo.data.remote.dto.MaterialListResponse
import com.mamay.eduvark_newrepo.data.remote.dto.MaterialResponse
import com.mamay.eduvark_newrepo.data.remote.dto.MeResponse
import com.mamay.eduvark_newrepo.data.remote.dto.RefreshRequest
import com.mamay.eduvark_newrepo.data.remote.dto.RefreshResponse
import com.mamay.eduvark_newrepo.data.remote.dto.ReorderTopicsRequest
import com.mamay.eduvark_newrepo.data.remote.dto.ResetPasswordRequest
import com.mamay.eduvark_newrepo.data.remote.dto.SaveFeedbackRequest
import com.mamay.eduvark_newrepo.data.remote.dto.SubmissionDetailResponse
import com.mamay.eduvark_newrepo.data.remote.dto.SubmitCaseRequest
import com.mamay.eduvark_newrepo.data.remote.dto.SubmissionResponse
import com.mamay.eduvark_newrepo.data.remote.dto.StudentDashboardResponse
import com.mamay.eduvark_newrepo.data.remote.dto.TopicAnalyticsResponse
import com.mamay.eduvark_newrepo.data.remote.dto.TopicListResponse
import com.mamay.eduvark_newrepo.data.remote.dto.TopicResponse
import com.mamay.eduvark_newrepo.data.remote.dto.UpdateAdminUserRequest
import com.mamay.eduvark_newrepo.data.remote.dto.UpdateMaterialRequest
import com.mamay.eduvark_newrepo.data.remote.dto.UpdateTopicRequest
import com.mamay.eduvark_newrepo.data.remote.dto.UpsertCaseRequest
import com.mamay.eduvark_newrepo.data.remote.dto.VarkRequest
import com.mamay.eduvark_newrepo.data.remote.dto.VarkResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface EduVarkApi {
    @GET("health")
    suspend fun health(): HealthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("auth/me")
    suspend fun me(): MeResponse

    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<Unit>

    @POST("auth/vark")
    suspend fun selectVark(@Body request: VarkRequest): VarkResponse

    @GET("topics")
    suspend fun listTopics(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50,
    ): TopicListResponse

    @GET("topics/{id}")
    suspend fun getTopic(@Path("id") topicId: String): TopicResponse

    @GET("lecturer/topics/{id}")
    suspend fun getLecturerTopic(@Path("id") topicId: String): TopicResponse

    @GET("student/dashboard")
    suspend fun getStudentDashboard(): StudentDashboardResponse

    @GET("topics/{id}/materials")
    suspend fun listMaterials(@Path("id") topicId: String): MaterialListResponse

    @GET("topics/{id}/feedback")
    suspend fun getFeedback(@Path("id") topicId: String): FeedbackResponse

    @PUT("topics/{id}/feedback")
    suspend fun saveFeedback(
        @Path("id") topicId: String,
        @Body request: SaveFeedbackRequest,
    ): FeedbackResponse

    @GET("topics/{id}/case")
    suspend fun getStudentCase(@Path("id") topicId: String): CaseResponse

    @POST("topics/{id}/case/submission")
    suspend fun submitCase(
        @Path("id") topicId: String,
        @Body request: SubmitCaseRequest,
    ): SubmissionResponse

    @GET("lecturer/topics/{id}/case")
    suspend fun getLecturerCase(@Path("id") topicId: String): CaseResponse

    @PUT("lecturer/topics/{id}/case")
    suspend fun upsertCase(
        @Path("id") topicId: String,
        @Body request: UpsertCaseRequest,
    ): CaseResponse

    @GET("lecturer/cases/{id}/submissions")
    suspend fun listCaseSubmissions(
        @Path("id") caseId: String,
    ): CaseSubmissionListResponse

    @GET("submissions/{id}")
    suspend fun getSubmission(@Path("id") submissionId: String): SubmissionDetailResponse

    @POST("submissions/{id}/assessment")
    suspend fun createAssessment(
        @Path("id") submissionId: String,
        @Body request: CreateAssessmentRequest,
    ): AssessmentResponse

    @GET("lecturer/analytics/topics/{id}")
    suspend fun getTopicAnalytics(
        @Path("id") topicId: String,
        @Query("category") category: String? = null,
        @Query("studentId") studentId: String? = null,
    ): TopicAnalyticsResponse

    @GET("admin/users")
    suspend fun listAdminUsers(
        @Query("q") query: String? = null,
        @Query("role") role: String? = null,
        @Query("status") status: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
    ): AdminUserListResponse

    @POST("admin/users")
    suspend fun createAdminUser(
        @Body request: CreateAdminUserRequest,
    ): AdminUserResponse

    @GET("admin/users/{id}")
    suspend fun getAdminUser(@Path("id") userId: String): AdminUserResponse

    @PATCH("admin/users/{id}")
    suspend fun updateAdminUser(
        @Path("id") userId: String,
        @Body request: UpdateAdminUserRequest,
    ): AdminUserResponse

    @POST("admin/users/{id}/reset-password")
    suspend fun resetAdminPassword(
        @Path("id") userId: String,
        @Body request: ResetPasswordRequest,
    ): Response<Unit>

    @POST("admin/users/{id}/deactivate")
    suspend fun deactivateAdminUser(@Path("id") userId: String): Response<Unit>

    @PATCH("admin/enrollments/{id}")
    suspend fun updateEnrollment(
        @Path("id") enrollmentId: String,
        @Body request: EnrollmentStatusRequest,
    ): EnrollmentResponse

    @POST("lecturer/topics")
    suspend fun createTopic(@Body request: CreateTopicRequest): TopicResponse

    @PATCH("lecturer/topics/{id}")
    suspend fun updateTopic(
        @Path("id") topicId: String,
        @Body request: UpdateTopicRequest,
    ): TopicResponse

    @POST("lecturer/topics/{id}/publish")
    suspend fun publishTopic(@Path("id") topicId: String): TopicResponse

    @POST("lecturer/topics/{id}/unpublish")
    suspend fun unpublishTopic(@Path("id") topicId: String): TopicResponse

    @DELETE("lecturer/topics/{id}")
    suspend fun deleteTopic(@Path("id") topicId: String): Response<Unit>

    @PATCH("lecturer/topics/reorder")
    suspend fun reorderTopics(@Body request: ReorderTopicsRequest): Response<Unit>

    /**
     * Unduh berkas materi lewat alamat tetap EduVARK. Setiap panggilan memakai
     * Authorization Bearer dan backend memeriksa izin ulang (FR-015/C1-02).
     */
    @Streaming
    @GET("materials/{id}/content")
    suspend fun downloadMaterialContent(@Path("id") materialId: String): ResponseBody

    /** Minta tujuan tautan eksternal setelah backend memeriksa izin terkini. */
    @GET("materials/{id}/link-target")
    suspend fun getMaterialLinkTarget(@Path("id") materialId: String): MaterialLinkTargetResponse

    @GET("lecturer/topics/{id}/materials")
    suspend fun listLecturerMaterials(@Path("id") topicId: String): MaterialListResponse

    @POST("lecturer/topics/{id}/materials/link")
    suspend fun createLinkMaterial(
        @Path("id") topicId: String,
        @Body request: LinkMaterialRequest,
    ): MaterialResponse

    @Multipart
    @POST("lecturer/topics/{id}/materials/file")
    suspend fun uploadFileMaterial(
        @Path("id") topicId: String,
        @Part file: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("vark") vark: RequestBody,
    ): MaterialResponse

    @PATCH("lecturer/materials/{id}")
    suspend fun updateMaterial(
        @Path("id") materialId: String,
        @Body request: UpdateMaterialRequest,
    ): MaterialResponse

    @DELETE("lecturer/materials/{id}")
    // Response<T> agar backend lama yang membalas 204 (tanpa body) tetap dapat
    // diperlakukan sebagai aksi materi yang berhasil dengan status fisik UNKNOWN.
    suspend fun deleteMaterial(
        @Path("id") materialId: String,
    ): Response<MaterialDeletionResponse>

    @Multipart
    @POST("lecturer/materials/{id}/file")
    suspend fun replaceMaterialFile(
        @Path("id") materialId: String,
        @Part file: MultipartBody.Part,
    ): MaterialFileReplacementResponse
}

interface RefreshApi {
    @POST("auth/refresh")
    suspend fun refresh(@Body request: RefreshRequest): RefreshResponse
}
