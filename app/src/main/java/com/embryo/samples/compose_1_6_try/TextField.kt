package com.embryo.samples.compose_1_6_try

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.embryo.commons.OnClick
import com.embryo.commons.home.SampleScaffold

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldSample(
    onBackClick: OnClick,
) {
    SampleScaffold(
        title = "TextField",
        onBackClick = onBackClick
    ) {
        val state = rememberTextFieldState()

        BasicTextField2(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                .padding(16.dp),
            state = state
        )
    }
}