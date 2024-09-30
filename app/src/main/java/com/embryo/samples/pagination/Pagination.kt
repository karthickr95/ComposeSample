package com.embryo.samples.pagination

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.embryo.commons.home.SampleScaffold
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Composable
fun PaginationSample(
    viewModel: PaginationViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Pagination",
        onBackClick = onBackClick,
        actions = {
            TextButton(onClick = viewModel::onAddClick) {
                Text(text = "Add")
            }
        }
    ) {
        val items = viewModel.items.collectAsStateWithLifecycle().value
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(
                items = items,
                key = { it },
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(1.dp, Color.Black)
                        .wrapContentHeight(),
                    text = it.toString()
                )
            }
        }
    }
}

@HiltViewModel
class PaginationViewModel @Inject constructor() : ViewModel() {

    private val _items: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<Int>> = _items.asStateFlow()
    private var lastTick = 0

    // todo try with SnapshotStateList
    val items2: SnapshotStateList<Int> = mutableStateListOf()

    init {
        viewModelScope.launch {
            val list = buildList {
                for (i in 100..200) {
                    add(i)
                }
            }
            lastTick = 100
            _items.value = list
        }
    }

    fun onAddClick() {
        if (lastTick == 0) return
        viewModelScope.launch {
            val list = buildList {
                val initial = lastTick - 10
                val final = lastTick - 1
                for (i in initial..final) {
                    add(i)
                }
                lastTick = initial
                addAll(_items.value)
            }
            _items.value = list
        }
    }
}