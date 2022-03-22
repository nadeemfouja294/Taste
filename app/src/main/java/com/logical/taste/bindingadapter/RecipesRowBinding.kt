package com.logical.taste.bindingadapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.logical.taste.R
import com.logical.taste.models.Result
import com.logical.taste.ui.fragments.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup
import java.lang.Exception

class RecipesRowBinding {

    companion object{

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout:ConstraintLayout,result: Result){
            recipeRowLayout.setOnClickListener {
                try {
                    val action=RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)

                }catch (e:Exception){
                    Log.i("onRecipeClickListener",e.message.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView,imageUrl:String){
            imageView.load(imageUrl) {
                crossfade(600)
                    .error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNoOfLikes")
        @JvmStatic
        fun setNoOfLikes(textView: TextView,likes:Int){
            textView.text=likes.toString()
        }

        @BindingAdapter("setNoOfMinutes")
        @JvmStatic
        fun setNoOfMinutes(textView: TextView,minutes:Int){
            textView.text=minutes.toString()
        }
        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View,vegan:Boolean){
            if(vegan){
                when(view){
                    is TextView->{
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }
                   is ImageView->{
                        view.setColorFilter(ContextCompat.getColor(view.context,R.color.green))
                    }
                }
            }
        }


        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView,summary:String){
            val plainText=Jsoup.parse(summary).text()
            textView.text=plainText
        }
    }
}