package com.embryo.samples.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.embryo.commons.OnClick
import com.embryo.navigation.Routes
import com.embryo.samples.R
import com.embryo.samples.SampleScaffold

@Composable
fun HomeScreen(
    routes: Array<String>,
    onNavClick: (String) -> Unit,
    onBackClick: OnClick,
    @StringRes titleRes: Int = R.string.home_screen_title,
) {
    SampleScaffold(
        titleRes = titleRes,
        onBackClick = onBackClick,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val routeItemModifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
            items(
                items = routes,
                key = { it }
            ) { route ->
                RouteItem(
                    modifier = routeItemModifier,
                    route = route,
                    onClick = { onNavClick(route) }
                )
            }
        }
    }
}

@Composable
private fun RouteItem(
    route: String,
    onClick: OnClick,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = route,
                fontSize = 16.sp,
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = route
            )
        }
    }
}

@Preview(name = "HomeScreen")
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(
        routes = Routes.allRoutes,
        onBackClick = {},
        onNavClick = {}
    )
}