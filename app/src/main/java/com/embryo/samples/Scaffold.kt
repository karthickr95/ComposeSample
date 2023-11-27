package com.embryo.samples

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.embryo.commons.CommonStrings

@Composable
fun SampleScaffold(
    @StringRes titleRes: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    scrollable: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = stringResource(CommonStrings.back)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(titleRes))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .then(
                    if (scrollable) Modifier.verticalScroll(rememberScrollState())
                    else Modifier
                ),
            verticalArrangement = verticalArrangement,
            content = content,
        )
    }
}
