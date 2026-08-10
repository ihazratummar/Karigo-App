package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobPaymentModel
import kotlinx.coroutines.flow.Flow

class GetJobPaymentsUseCase(
    private val jobRepository: JobRepository
) {
    operator fun invoke(jobId: String): Flow<Result<List<JobPaymentModel>, JobError>> {
        return jobRepository.getPaymentsForJob(jobId)
    }
}

class AddJobPaymentUseCase(
    private val jobRepository: JobRepository
) {
    suspend operator fun invoke(payment: JobPaymentModel): Result<Unit, JobError> {
        return jobRepository.addPayment(payment)
    }
}

class DeleteJobPaymentUseCase(
    private val jobRepository: JobRepository
) {
    suspend operator fun invoke(paymentId: String): Result<Unit, JobError> {
        return jobRepository.deletePayment(paymentId)
    }
}
