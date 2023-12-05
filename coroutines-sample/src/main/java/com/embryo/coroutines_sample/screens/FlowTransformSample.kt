package com.embryo.coroutines_sample.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.embryo.commons.home.SampleScaffold

@Composable
internal fun FlowTransformSample(
    viewModel: CoroutineSampleViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Flow Transform",
        onBackClick = onBackClick,
    ) {

        val value = viewModel.flow.collectAsStateWithLifecycle().value

        Text(
            text = "Current Value $value"
        )

        Button(
            onClick = {},
        ) {
            Text("Change Name")
        }
    }
}