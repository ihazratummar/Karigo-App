package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobLabourLogModel
import kotlinx.coroutines.flow.Flow

class GetJobLabourLogsUseCase(
    private val jobRepository: JobRepository
) {
    operator fun invoke(labourItemId: String): Flow<Result<List<JobLabourLogModel>, JobError>> {
        return jobRepository.getLabourLogs(labourItemId)
    }
}
