package com.logical.taste.adapter

import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.logical.taste.R
import com.logical.taste.data.database.entities.FavouriteEntity
import com.logical.taste.databinding.FavouriteRecipesRowLayoutBinding
import com.logical.taste.ui.fragments.favourites.FavouriteRecipesFragmentDirections
import com.logical.taste.util.RecipesDiffUtil
import com.logical.taste.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.favourite_recipes_row_layout.view.*

class FavouriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavouriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var mActionMode: ActionMode
    //TODO difference between array,list and arrayList
    private var selectedRecipes = arrayListOf<FavouriteEntity>()
    private var favouriteRecipes = emptyList<FavouriteEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()

    init {

    }
    class MyViewHolder(private val binding: FavouriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteEntity: FavouriteEntity) {
            binding.favouriteEntity = favouriteEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    FavouriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        val currentRecipe = favouriteRecipes[position]
        holder.bind(currentRecipe)

        /**
         * Single click listener*
         **/

        holder.itemView.favouriteRecipesRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }
        /**
         * Long click listener*
         **/

        holder.itemView.favouriteRecipesRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                requireActivity.startActionMode(this)
                multiSelection = true
                applySelection(holder, currentRecipe)

                true
            } else {
                multiSelection = false
                false
            }
        }

    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavouriteEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.favouriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.itemView.favourite_row_cardView.strokeColor = ContextCompat.getColor(
            requireActivity,
            strokeColor
        )
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourite_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        //TODO difference between ? and !!
        //TODO food joke fragment not shown
        if(item?.itemId==R.id.delete_favourite_recipe_menu){
            selectedRecipes.forEach{
              mainViewModel.deleteFavouriteRecipe(it)

            }
            Toast.makeText(requireActivity, "${selectedRecipes.size} recipe/s deleted", Toast.LENGTH_SHORT).show()
            multiSelection=false
            selectedRecipes.clear()
            mode?.finish()

        }

        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        // Set to original colors/mode
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        applyStatusBarColor(R.color.statusBarColor)
        myViewHolders.clear()
        selectedRecipes.clear()
        multiSelection = false
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }


    fun setData(newFavouriteRecipes: List<FavouriteEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(favouriteRecipes, newFavouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favouriteRecipes = newFavouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()

        }
    }

}