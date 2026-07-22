package com.karigojobs.app.feature.job.joblist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigojobs.presentation.job.jobList.JobListIntent
import com.karigojobs.presentation.job.jobList.JobListState
import com.karigojobs.ui.common.JobCard
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.common.TopBarTitle
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_label_search
import karigojobs.shared.resources.generated.resources.common_total
import karigojobs.shared.resources.generated.resources.common_total_count
import karigojobs.shared.resources.generated.resources.job_list_search_field
import karigojobs.shared.resources.generated.resources.nav_jobs
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 25/05/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(
    modifier: Modifier = Modifier,
    jobListState: JobListState,
    onJobClick: (String) -> Unit,
    event: (JobListIntent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column(
                modifier = Modifier
                    .padding(horizontal = dimens.Padding.base)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TopBarTitle(title = stringResource(Res.string.nav_jobs))
                    Text(
                        text = stringResource(Res.string.common_total_count, jobListState.jobs.size.toLocaleString()),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                KarigojobsSearchField(
                    query = jobListState.searchJobText,
                    onQueryChange = { event(JobListIntent.SearchTextChanged(it)) },
                    placeholder = stringResource(Res.string.job_list_search_field)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).padding(vertical = dimens.Padding.lg)
        ) {
            if (jobListState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = dimens.Padding.base)
                ) {
                    items(jobListState.jobs, key = { it.id }) { job ->
                        JobCard(
                            job = job,
                            onClick = { onJobClick(job.id) }
                        )
                    }
                }
            }
        }
    }
}