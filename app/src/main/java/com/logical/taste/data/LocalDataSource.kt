package com.logical.taste.data

import com.logical.taste.data.database.RecipesDao
import com.logical.taste.data.database.entities.FavouritesEntity
import com.logical.taste.data.database.entities.FoodJokeEntity
import com.logical.taste.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
  private val recipesDao: RecipesDao
){

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }


    // For favourite recipes

    suspend fun insertFavouriteRecipe(favouriteEntity: FavouritesEntity){
        recipesDao.insertFavouriteRecipe(favouriteEntity)
    }

    fun readFavouriteRecipes():Flow<List<FavouritesEntity>>{
        return recipesDao.readFavouriteRecipes()
    }

    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouritesEntity){
        recipesDao.deleteFavouriteRecipe(favouriteEntity)
    }

    suspend fun deleteAllFavouriteRecipes(){
        recipesDao.deleteAllFavouriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }
}