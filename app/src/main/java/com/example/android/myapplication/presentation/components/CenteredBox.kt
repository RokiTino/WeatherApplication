package com.example.android.myapplication.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun CenteredBox(modifier: Modifier, content:@Composable () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        content()
    }

}