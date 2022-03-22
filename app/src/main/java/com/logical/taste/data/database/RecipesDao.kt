package com.logical.taste.data.database

import androidx.room.*
import com.logical.taste.data.database.entities.FavouriteEntity
import com.logical.taste.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity)

    @Query("SELECT *FROM recipes_table ORDER BY id ASC")
    fun readRecipes():Flow<List<RecipesEntity>>

    @Query("SELECT *FROM favourite_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipes():Flow<List<FavouriteEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()
}