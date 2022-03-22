package com.logical.taste.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.logical.taste.models.FoodRecipe
import com.logical.taste.models.Result

class RecipesTypeConverter {

    private var gson=Gson()
    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe):String{
        return gson.toJson(foodRecipe)

    }

    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipe{
        val listType=object :TypeToken<FoodRecipe>(){}.type
        return  gson.fromJson(data,listType)
    }

    @TypeConverter
    fun resultToString(result:Result):String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data:String):Result{
        val listType=object :TypeToken<Result>(){}.type
        return  gson.fromJson(data,listType)
    }
}