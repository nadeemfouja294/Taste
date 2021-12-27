package com.logical.taste.data

import com.logical.taste.models.FoodRecipe
import com.logical.taste.data.network.FoodRecipeApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipeApi: FoodRecipeApi){

    suspend fun getRecipes(queries:Map<String,String>):Response<FoodRecipe>{
        return foodRecipeApi.getRecipes(queries)
    }

}