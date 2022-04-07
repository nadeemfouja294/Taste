package com.logical.taste.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.logical.taste.models.Result
import com.logical.taste.util.Constants.Companion.FAVOURITE_RECIPES_TABLE


@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result:Result
)