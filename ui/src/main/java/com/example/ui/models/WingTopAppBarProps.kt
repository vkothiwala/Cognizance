package com.example.ui.models

data class WingTopAppBarProps(
    val title: String,
    val navigationProps: WingTopAppBarNavigationProps? = null,
    val actionProps: List<WingTopAppBarActionProps> = emptyList()
)
