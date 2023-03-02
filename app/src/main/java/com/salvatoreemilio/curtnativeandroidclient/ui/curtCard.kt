package com.salvatoreemilio.curtnativeandroidclient.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import com.salvatoreemilio.curtnativeandroidclient.model.Curt

@Composable
fun CurtCard(curt: Curt) {
    val uriHandler = LocalUriHandler.current
    CurtCardUI(curt = curt, uriHandler = uriHandler)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurtCardUI(curt: Curt, uriHandler: UriHandler) {
    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(10.dp, 5.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(15.dp)
        ) {
            TextButton(onClick = {
                uriHandler.openUri(curt.url)
            }) {
                Text(text = curt.url)
            }
            TextButton(onClick = { uriHandler.openUri(curt.curt) }) {
                Text(text = curt.curt)
            }
        }
    }
}