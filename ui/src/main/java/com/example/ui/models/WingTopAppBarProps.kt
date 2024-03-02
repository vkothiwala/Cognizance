package com.example.ui.models

data class WingTopAppBarProps(
    val title: String,
    val onBackPress: () -> Unit,
    val actionProps: List<WingTopAppBarActionProps> = emptyList()
)
