package com.logical.taste.data

import com.logical.taste.data.database.RecipesDao
import com.logical.taste.data.database.entities.FavouriteEntity
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

    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity){
        recipesDao.insertFavouriteRecipe(favouriteEntity)
    }

    fun readFavouriteRecipes():Flow<List<FavouriteEntity>>{
        return recipesDao.readFavouriteRecipes()
    }

    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity){
        recipesDao.deleteFavouriteRecipe(favouriteEntity)
    }

    suspend fun deleteAllFavouriteRecipes(){
        recipesDao.deleteAllFavouriteRecipes()
    }
}