package com.embryo.commons

inline fun trySafely(lambda: () -> Unit) {
    try {
        lambda()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

inline fun <T> trySafely(defaultValue: T, lambda: () -> T) : T {
    return try {
        lambda()
    } catch (e: Exception) {
        e.printStackTrace()
        defaultValue
    }
}