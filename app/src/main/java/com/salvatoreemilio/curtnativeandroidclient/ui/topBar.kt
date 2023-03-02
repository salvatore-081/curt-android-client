package com.salvatoreemilio.curtnativeandroidclient.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.salvatoreemilio.curtnativeandroidclient.data.SettingsStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val context = LocalContext.current;
    val store = SettingsStore(context);
    val host = runBlocking { store.getHost.first() }

    val showDialog = remember { mutableStateOf(host.isBlank()) }

    if (showDialog.value) {
        SettingsDialog(showDialog)
    }
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Curt Client",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { showDialog.value = true }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Impostazioni"
                )
            }
        }
    )

}