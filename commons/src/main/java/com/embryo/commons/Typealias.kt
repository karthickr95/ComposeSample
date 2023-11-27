package com.embryo.commons


/**
 * Use this typealias [OnClick] in the [androidx.compose.ui.*] for onClick lambdas
 */
typealias OnClick = () -> Unit

/**
 * Use this typealias [Callback] in situations of just to
 * trigger callback with no input and return value
 */
typealias Callback = () -> Unit

@Suppress("RemoveRedundantQualifierName")
typealias CommonStrings = com.embryo.commons.R.string