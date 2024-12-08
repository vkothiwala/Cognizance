package com.example.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WingSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    var isSearching by rememberSaveable { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = isSearching,
        onActiveChange = { isSearching = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(SearchBarDefaults.dockedShape),
        shape = SearchBarDefaults.dockedShape,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        isSearching = true
                    }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        isSearching = false
                        onQueryChange("")
                    },
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            dividerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        content()
    }
}
