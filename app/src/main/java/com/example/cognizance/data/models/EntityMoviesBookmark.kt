package com.example.cognizance.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_bookmark")
data class EntityMoviesBookmark(
    @PrimaryKey var id: Int
)
