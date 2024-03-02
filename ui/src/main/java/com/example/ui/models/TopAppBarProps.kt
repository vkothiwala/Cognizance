package com.example.ui.models

data class TopAppBarProps(
    val title: String,
    val onBackPress: () -> Unit,
    val actionProps: List<AppBarActionProps> = emptyList()
)
