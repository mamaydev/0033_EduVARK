package com.mamay.eduvark_newrepo.core.di

import com.mamay.eduvark_newrepo.data.repository.AuthRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.AdminRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.AnalyticsRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.AssessmentRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.CaseRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.FeedbackRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.HealthRepositoryImpl
import com.mamay.eduvark_newrepo.data.repository.TopicRepositoryImpl
import com.mamay.eduvark_newrepo.core.files.AndroidMaterialFileStore
import com.mamay.eduvark_newrepo.core.files.HttpMaterialContentSource
import com.mamay.eduvark_newrepo.domain.repository.AdminRepository
import com.mamay.eduvark_newrepo.domain.repository.AnalyticsRepository
import com.mamay.eduvark_newrepo.domain.repository.AssessmentRepository
import com.mamay.eduvark_newrepo.domain.repository.AuthRepository
import com.mamay.eduvark_newrepo.domain.repository.CaseRepository
import com.mamay.eduvark_newrepo.domain.repository.FeedbackRepository
import com.mamay.eduvark_newrepo.domain.repository.HealthRepository
import com.mamay.eduvark_newrepo.domain.repository.MaterialContentSource
import com.mamay.eduvark_newrepo.domain.repository.MaterialFileStore
import com.mamay.eduvark_newrepo.domain.repository.TopicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHealthRepository(impl: HealthRepositoryImpl): HealthRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTopicRepository(impl: TopicRepositoryImpl): TopicRepository

    @Binds
    @Singleton
    abstract fun bindMaterialFileStore(impl: AndroidMaterialFileStore): MaterialFileStore

    @Binds
    @Singleton
    abstract fun bindMaterialContentSource(impl: HttpMaterialContentSource): MaterialContentSource

    @Binds
    @Singleton
    abstract fun bindFeedbackRepository(impl: FeedbackRepositoryImpl): FeedbackRepository

    @Binds
    @Singleton
    abstract fun bindCaseRepository(impl: CaseRepositoryImpl): CaseRepository

    @Binds
    @Singleton
    abstract fun bindAssessmentRepository(impl: AssessmentRepositoryImpl): AssessmentRepository

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepository(impl: AnalyticsRepositoryImpl): AnalyticsRepository

    @Binds
    @Singleton
    abstract fun bindAdminRepository(impl: AdminRepositoryImpl): AdminRepository
}
