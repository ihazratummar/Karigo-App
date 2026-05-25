package com.karigojobs.presentation.job.jobList

import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus


/**
 * @author hazratummar
 * Created on 25/05/26
 */


data class JobListState(
    val isLoading: Boolean = false,
    val jobStatusFilter: JobStatusFilter = JobStatusFilter.All,
    val jobs: List<JobModel> = emptyList(),
    val searchJobText: String = ""
)

sealed interface JobStatusFilter {
    data object All : JobStatusFilter
    data class Status(
        val status: JobStatus
    ) : JobStatusFilter
}


sealed interface JobListIntent {
    data class JobFilterClick(val filter: JobStatusFilter) : JobListIntent
    data class SearchTextChanged(val text: String) : JobListIntent
    data class JobClick(val jobId: String) : JobListIntent
}

sealed interface JobListEffect {
    data class OnJobClick(val jobId: String) : JobListEffect
    data class Error(val message: String) : JobListEffect
}