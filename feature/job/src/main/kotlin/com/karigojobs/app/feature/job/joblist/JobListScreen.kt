package com.karigojobs.app.feature.job.joblist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigojobs.presentation.job.jobList.JobListState
import com.karigojobs.ui.common.JobCard
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 25/05/26
 */


@Composable
fun JobListScreen(
    modifier: Modifier = Modifier,
    jobListState: JobListState
) {

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (jobListState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = dimens.Padding.base)
                ) {
                    items(jobListState.jobs, key = { it.id }) { job ->
                        JobCard(job = job)
                    }
                }
            }
        }
    }
}