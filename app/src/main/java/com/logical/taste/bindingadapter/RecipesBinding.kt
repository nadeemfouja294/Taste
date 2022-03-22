package com.logical.taste.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.logical.taste.data.database.entities.RecipesEntity
import com.logical.taste.models.FoodRecipe
import com.logical.taste.util.NetworkResult

class RecipesBinding {

    companion object{
        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse:NetworkResult<FoodRecipe>?,
            database:List<RecipesEntity>?
        ){
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else  {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse1","readDatabase1", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse:NetworkResult<FoodRecipe>?,
            database:List<RecipesEntity>?
        ){
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else {
                textView.visibility = View.INVISIBLE
            }
        }
    }

}