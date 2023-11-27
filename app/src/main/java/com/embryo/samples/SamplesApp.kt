package com.embryo.samples

import androidx.compose.runtime.Composable
import com.embryo.commons.Callback
import com.embryo.samples.navigation.SamplesNavHost
import com.embryo.samples.theme.SamplesTheme

@Composable
fun SamplesApp(
    onCloseAppAction: Callback,
) {
    SamplesTheme {
        SamplesNavHost(
            onCloseAppAction = onCloseAppAction
        )
    }
}