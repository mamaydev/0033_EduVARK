package com.mamay.eduvark_newrepo.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamay.eduvark_newrepo.domain.error.AppFailure
import com.mamay.eduvark_newrepo.domain.model.AdminAccountStatus
import com.mamay.eduvark_newrepo.domain.model.CaseStudy
import com.mamay.eduvark_newrepo.domain.model.AdminUser
import com.mamay.eduvark_newrepo.domain.model.StudentDashboard
import com.mamay.eduvark_newrepo.domain.model.Topic
import com.mamay.eduvark_newrepo.domain.model.TopicStatus
import com.mamay.eduvark_newrepo.domain.model.VarkType
import com.mamay.eduvark_newrepo.domain.model.UserProfile
import com.mamay.eduvark_newrepo.domain.model.UserRole
import com.mamay.eduvark_newrepo.domain.usecase.GetMeUseCase
import com.mamay.eduvark_newrepo.domain.usecase.GetStudentCaseUseCase
import com.mamay.eduvark_newrepo.domain.usecase.GetStudentDashboardUseCase
import com.mamay.eduvark_newrepo.domain.usecase.GetTopicsUseCase
import com.mamay.eduvark_newrepo.domain.usecase.ListAdminUsersUseCase
import com.mamay.eduvark_newrepo.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AdminSummary(
    val total: Int,
    val active: Int,
    val inactive: Int,
    val students: Int,
    val lecturers: Int,
    val admins: Int,
) {
    companion object {
        fun from(users: List<AdminUser>, fallbackTotal: Int): AdminSummary {
            val students = users.count { it.role == UserRole.STUDENT }
            val lecturers = users.count { it.role == UserRole.LECTURER }
            val admins = users.count { it.role == UserRole.ADMIN }
            return AdminSummary(
                total = maxOf(fallbackTotal, users.size),
                active = users.count { it.status == AdminAccountStatus.ACTIVE },
                inactive = users.count { it.status == AdminAccountStatus.INACTIVE },
                students = students,
                lecturers = lecturers,
                admins = admins,
            )
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val user: UserProfile? = null,
    val topics: List<Topic> = emptyList(),
    val studentDashboard: StudentDashboard? = null,
    val casePreviews: Map<String, CaseStudy> = emptyMap(),
    val adminUsers: List<AdminUser> = emptyList(),
    val adminSummary: AdminSummary? = null,
    val error: String? = null,
    val refreshError: String? = null,
    val loggedOut: Boolean = false,
) {
    val publishedTopics: List<Topic>
        get() = topics.filter { it.status == TopicStatus.PUBLISHED }

    val draftTopics: List<Topic>
        get() = topics.filter { it.status == TopicStatus.DRAFT }

    val readyTopics: List<Topic>
        get() = topics.filter { it.materialAvailable }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMeUseCase: GetMeUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getTopicsUseCase: GetTopicsUseCase,
    private val getStudentDashboardUseCase: GetStudentDashboardUseCase,
    private val listAdminUsersUseCase: ListAdminUsersUseCase,
    private val getStudentCaseUseCase: GetStudentCaseUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private var requestInFlight = false

    init {
        load()
    }

    fun load() = fetch(showFullLoading = true)

    fun refresh() = fetch(showFullLoading = false)

    private fun fetch(showFullLoading: Boolean) {
        if (requestInFlight) return
        requestInFlight = true
        viewModelScope.launch {
            _state.update {
                if (showFullLoading) {
                    it.copy(
                        isLoading = true,
                        error = null,
                        refreshError = null,
                        loggedOut = false,
                    )
                } else {
                    it.copy(
                        isRefreshing = true,
                        error = null,
                        refreshError = null,
                        loggedOut = false,
                    )
                }
            }
            try {
                val user = getMeUseCase()
                _state.update {
                    it.copy(user = user, isLoading = false, isRefreshing = false)
                }
                loadDashboardData(user)
            } catch (error: AppFailure) {
                if (error is AppFailure.Api && error.statusCode == 401) {
                    _state.update {
                        it.copy(isLoading = false, isRefreshing = false, loggedOut = true)
                    }
                } else {
                    _state.update {
                        if (it.user == null) {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = error.message,
                            )
                        } else {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                refreshError = error.message,
                            )
                        }
                    }
                }
            } catch (error: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = "Terjadi kesalahan. Coba lagi.",
                    )
                }
            } finally {
                requestInFlight = false
            }
        }
    }

    /**
     * Dashboard counters are a nice-to-have: a failure here must not block the
     * home screen, so each call is isolated.
     */
    private suspend fun loadDashboardData(user: UserProfile) = coroutineScope {
        if (user.role == UserRole.ADMIN) {
            async {
                runCatching {
                    listAdminUsersUseCase(
                        query = null,
                        role = null,
                        status = null,
                        page = 1,
                        pageSize = 100,
                    )
                }.onSuccess { page ->
                    _state.update {
                        it.copy(
                            adminSummary = AdminSummary.from(page.items, page.total),
                            adminUsers = page.items.take(4),
                            topics = emptyList(),
                        )
                    }
                }
            }
        } else {
            async {
                runCatching { getTopicsUseCase() }.onSuccess { topics ->
                    _state.update { it.copy(topics = topics) }
                    loadCasePreviews(user, topics)
                }
            }
            if (user.role == UserRole.STUDENT) {
                async {
                    runCatching { getStudentDashboardUseCase() }.onSuccess { dashboard ->
                        _state.update { it.copy(studentDashboard = dashboard) }
                    }
                }
            }
        }
        Unit
    }

    /**
     * Kinetic students see the case state on the home cards. Only the two cards
     * that are actually rendered need the extra calls.
     */
    private suspend fun loadCasePreviews(user: UserProfile, topics: List<Topic>) = coroutineScope {
        if (user.activeVark != VarkType.KINESTHETIC) return@coroutineScope
        val published = topics.filter { it.status == TopicStatus.PUBLISHED }
            .sortedBy { it.sortOrder }
            .take(2)
        published.map { topic ->
            async {
                val preview = runCatching { getStudentCaseUseCase(topic.id) }.getOrNull()
                preview?.let { _state.update { state -> state.copy(casePreviews = state.casePreviews + (topic.id to it)) } }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(refreshError = null) }
            try {
                logoutUseCase()
                _state.update { it.copy(loggedOut = true) }
            } catch (error: AppFailure) {
                // Keep the signed-in state when the revocation failed.
                _state.update { it.copy(refreshError = error.message) }
            } catch (_: Exception) {
                _state.update {
                    it.copy(refreshError = "Terjadi kesalahan. Coba lagi.")
                }
            }
        }
    }

    fun consumeLoggedOut() {
        _state.update { it.copy(loggedOut = false) }
    }
}
