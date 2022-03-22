package com.logical.taste.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.logical.taste.models.FoodRecipe
import com.logical.taste.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe:FoodRecipe) {
  @PrimaryKey(autoGenerate = false)
    var id:Int=0

}