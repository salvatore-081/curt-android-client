package com.salvatoreemilio.curtnativeandroidclient.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.salvatoreemilio.curtnativeandroidclient.ui.theme.CurtNativeAndroidClientTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.salvatoreemilio.curtnativeandroidclient.data.SettingsStore
import com.salvatoreemilio.curtnativeandroidclient.ui.TopBar
import com.salvatoreemilio.curtnativeandroidclient.ui.CurtsList
import com.salvatoreemilio.curtnativeandroidclient.ui.views.CurtsViewModel
import com.salvatoreemilio.curtnativeandroidclient.ui.views.CurtsViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CurtNativeAndroidClientTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        TopBar()
                    }, content = { p -> MainContent(p = p) })
                }
            }
        }
    }

    @Composable
    fun MainContent(p: PaddingValues) {
        val context = LocalContext.current
        val store = SettingsStore(context)
        val host = store.getHost.collectAsState(initial = "")
        val xAPIKey = store.getXAPIKey.collectAsState(initial = "")
        if (host.value.isNotBlank()) {
            val curtsViewModel by viewModels<CurtsViewModel> { CurtsViewModelFactory(host.value, xAPIKey.value) }
            curtsViewModel.updateHost(host.value, xAPIKey.value)
            Box(modifier = Modifier.padding(p)) {
                CurtsList(curtsViewModel)
            }
        }
    }
}