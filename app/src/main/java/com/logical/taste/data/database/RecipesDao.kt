package com.logical.taste.data.database

import androidx.room.*
import com.logical.taste.data.database.entities.FavouritesEntity
import com.logical.taste.data.database.entities.FoodJokeEntity
import com.logical.taste.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouriteEntity: FavouritesEntity)

    @Query("SELECT *FROM recipes_table ORDER BY id ASC")
    fun readRecipes():Flow<List<RecipesEntity>>

    @Query("SELECT *FROM favourite_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipes():Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouritesEntity)

    @Query("DELETE FROM favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

}