package com.example.ui.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import com.example.ui.models.DeviceType

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController found!")
}

val LocalDeviceType = staticCompositionLocalOf<DeviceType> {
    error("Device type not configured!")
}
