package com.logical.taste.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.logical.taste.R
import com.logical.taste.data.database.entities.FavouritesEntity
import com.logical.taste.databinding.ActivityDetailsBinding
import com.logical.taste.ui.fragments.ingredients.IngredientsFragment
import com.logical.taste.ui.fragments.instructions.InstructionsFragment
import com.logical.taste.ui.fragments.overview.OverviewFragment
import com.logical.taste.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private  var _binding:ActivityDetailsBinding?=null
    private val binding get() = _binding!!
    private lateinit var star_menuItem:MenuItem
    private var recipeSaved = false
    private var savedRecipeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val pagerAdapter = com.logical.taste.adapter.PagerAdapter(
            resultBundle,
            fragments,
         this
        )
        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
         star_menuItem = menu!!.findItem(R.id.save_to_favourite_menu)
        //TODO change function name to checkFavouriteRecipes
        checkSavedRecipes(star_menuItem)
        return true
    }

    private fun checkSavedRecipes(star_menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipes.observe(this) { favouriteEntity ->
            try {
                for (savedRecipe in favouriteEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(star_menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true

                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favourite_menu && recipeSaved) {
            removeFromFavourites(item)
        } else if (item.itemId == R.id.save_to_favourite_menu && !recipeSaved) {
            saveToFavourites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouriteEntity = FavouritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavouriteRecipe(favouriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        recipeSaved = true
        Toast.makeText(this, "Recipe saved in favourites", Toast.LENGTH_SHORT).show()
    }

    private fun removeFromFavourites(item: MenuItem) {
        val favouriteEntity = FavouritesEntity(
            savedRecipeId,
            args.result
        )
        mainViewModel.deleteFavouriteRecipe(favouriteEntity)
        changeMenuItemColor(item, R.color.white)
        recipeSaved = false
        Toast.makeText(this, "Recipe removed from favourites", Toast.LENGTH_SHORT).show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))

    }


//
//    private val args by navArgs<DetailsActivityArgs>()
//    private val mainViewModel: MainViewModel by viewModels()
//
//    private var recipeSaved = false
//    private var savedRecipeId = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_details)
//
//        setSupportActionBar(toolBar)
//        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val fragments = ArrayList<Fragment>()
//        fragments.add(OverviewFragment())
//        fragments.add(IngredientsFragment())
//        fragments.add(InstructionsFragment())
//
//        val titles = ArrayList<String>()
//        titles.add("Overview")
//        titles.add("Ingredients")
//        titles.add("Instructions")
//
//        val resultBundle = Bundle()
//        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)
//
//        val adapter = PagerAdapter(
//            resultBundle,
//            fragments,
//            titles,
//            supportFragmentManager
//        )
//
//        viewPager.adapter = adapter
//        tabLayout.setupWithViewPager(viewPager)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.details_menu, menu)
//        val menuItem = menu?.findItem(R.id.save_to_favourite_menu)
//        checkSavedRecipes(menuItem!!)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            finish()
//        } else if (item.itemId == R.id.save_to_favourite_menu && !recipeSaved) {
//            saveToFavorites(item)
//        } else if (item.itemId == R.id.save_to_favourite_menu && recipeSaved) {
//            removeFromFavorites(item)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun checkSavedRecipes(menuItem: MenuItem) {
//        mainViewModel.readFavouriteRecipes.observe(this, { favoritesEntity ->
//            try {
//                for (savedRecipe in favoritesEntity) {
//                    if (savedRecipe.result.id == args.result.id) {
//                        changeMenuItemColor(menuItem, R.color.yellow)
//                        savedRecipeId = savedRecipe.id
//                        recipeSaved = true
//                    } else {
//                        changeMenuItemColor(menuItem, R.color.white)
//                    }
//                }
//            } catch (e: Exception) {
//                Log.d("DetailsActivity", e.message.toString())
//            }
//        })
//    }
//
//    private fun saveToFavorites(item: MenuItem) {
//        val favoritesEntity =
//            FavouriteEntity(
//                0,
//                args.result
//            )
//        mainViewModel.insertFavouriteRecipe(favoritesEntity)
//        changeMenuItemColor(item, R.color.yellow)
//        showSnackBar("Recipe saved.")
//        recipeSaved = true
//    }
//
//    private fun removeFromFavorites(item: MenuItem) {
//        val favoritesEntity =
//            FavouriteEntity(
//                savedRecipeId,
//                args.result
//            )
//        mainViewModel.deleteFavouriteRecipe(favoritesEntity)
//        changeMenuItemColor(item, R.color.white)
//        showSnackBar("Removed from Favorites.")
//        recipeSaved = false
//    }
//
//    private fun showSnackBar(message: String) {
//        Snackbar.make(
//            detailsLayout,
//            message,
//            Snackbar.LENGTH_SHORT
//        ).setAction("Okay") {}
//            .show()
//    }
//
//    private fun changeMenuItemColor(item: MenuItem, color: Int) {
//        item.icon.setTint(ContextCompat.getColor(this, color))
//    }
//
//
//
override fun onDestroy() {
    super.onDestroy()
    changeMenuItemColor(star_menuItem, R.color.white)
    _binding = null
}

}