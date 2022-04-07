package com.logical.taste.data

import com.logical.taste.models.FoodRecipe
import com.logical.taste.data.network.FoodRecipeApi
import com.logical.taste.models.FoodJoke
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipesApi: FoodRecipeApi){

    suspend fun getRecipes(queries:Map<String,String>):Response<FoodRecipe>{
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchQuery(searchQuery:Map<String,String>):Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String):Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apiKey)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }

}