package com.example.cognizance.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ui.models.AppBarActionProps

@Composable
fun AppBarActions(actionProps: List<AppBarActionProps>) {
    if (actionProps.isEmpty()) return
    var isActionMenuExpanded by remember { mutableStateOf(false) }
    IconButton(onClick = { isActionMenuExpanded = !isActionMenuExpanded }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null
        )
    }
    DropdownMenu(
        expanded = isActionMenuExpanded,
        onDismissRequest = { isActionMenuExpanded = false }
    ) {
        actionProps.forEach {
            DropdownMenuItem(
                text = {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = it.actionTitle
                    )
                },
                onClick = {
                    isActionMenuExpanded = false
                    it.onActionClick()
                }
            )
        }
    }
}
