package com.salvatoreemilio.curtnativeandroidclient.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import com.salvatoreemilio.curtnativeandroidclient.ui.views.CurtsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurtsList(curtsViewModel: CurtsViewModel) {
    val refreshing = curtsViewModel.loading.collectAsState().value
    val pullRefreshState = rememberPullRefreshState(refreshing, curtsViewModel::refresh)
    val err = curtsViewModel.err

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!refreshing && curtsViewModel.curtsResponse != null && err == null) {
                items(items = curtsViewModel.curtsResponse!!, itemContent = { item ->
                    CurtCard(curt = item)
                })
            }
            if (err != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(10.dp),

                        modifier = Modifier.padding(10.dp, 5.dp),

                        ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            var text = "Incorrect Host"
                            if (err.message.toString().trim().endsWith("401")) {
                                text = "Incorrect Key"
                            }
                            Text(
                                text = text,
                                color = MaterialTheme.colorScheme.error,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                        }

                    }

                }
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}