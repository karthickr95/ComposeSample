package com.embryo.coroutines_sample.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class CoroutineSampleViewModel @Inject constructor(

) : ViewModel() {

    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    @OptIn(ExperimentalCoroutinesApi::class)
    val flow: StateFlow<String> = flow {
        var currentValue = 1
        while (true) {
            emit(currentValue)
            currentValue += 1
            delay(5000)
        }
    }.flatMapLatest { value ->
       getNameFlow(value)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ""
    )

    private fun getRandomText(): String {
       return (1..5)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun getNameFlow(value: Int): Flow<String> {
        return flow {
            while (true) {
                emit(getRandomText() + "   " + value)
                delay(1000)
            }
        }
    }
}

internal class Repository @Inject constructor()