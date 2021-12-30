package com.logical.taste.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.logical.taste.R

class RecipesRowBinding {

    companion object{

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
    }
}