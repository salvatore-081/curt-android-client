package com.salvatoreemilio.curtnativeandroidclient.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.salvatoreemilio.curtnativeandroidclient.data.SettingsStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun SettingsDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = { openDialogCustom.value = false },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        SettingsDialogUI(openDialogCustom = openDialogCustom)
    }
}

//Layout
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>) {
    val context = LocalContext.current
    val store = SettingsStore(context)
    val host = remember { mutableStateOf("") }
    val key = remember { mutableStateOf("") }

    host.value = runBlocking { store.getHost.first() }
    key.value = runBlocking { store.getKey.first() }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
    ) {
        Column(
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = "Settings",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                TextFieldUI("Host", host)
                Spacer(modifier = modifier.height(5.dp))
                TextFieldUI(label = "Key", state = key)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.onBackground),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                androidx.compose.material3.TextButton(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveHost(host.value)
                        store.saveKey(key.value)
                    }
                    if (host.value.isBlank()) {
                        Toast.makeText(context, "Host is required!", Toast.LENGTH_SHORT).show()
                    } else {
                        openDialogCustom.value = false
                    }
                }) {
                    androidx.compose.material3.Text(
                        "Save",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldUI(label: String, state: MutableState<String>) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it;
        },
        label = { Text("$label") },
        modifier = Modifier.padding(10.dp),
        maxLines = 1,
        trailingIcon = {
            Icon(
                Icons.Default.Clear,
                contentDescription = "clear text",
                modifier = Modifier.clickable { state.value = "" })
        }
    )
}