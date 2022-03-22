package com.logical.taste.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.logical.taste.data.DataStoreRepository
import com.logical.taste.util.Constants.Companion.API_KEY
import com.logical.taste.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.logical.taste.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.logical.taste.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.logical.taste.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.logical.taste.util.Constants.Companion.QUERY_API_KEY
import com.logical.taste.util.Constants.Companion.QUERY_DIET
import com.logical.taste.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.logical.taste.util.Constants.Companion.QUERY_NUMBER
import com.logical.taste.util.Constants.Companion.QUERY_SEARCH
import com.logical.taste.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE
    var networkStatus = false
    var backOnline = false


    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnLine.asLiveData()
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)

        }
    }

    fun applyQueries(): HashMap<String, String> {
        viewModelScope.launch {
            readMealAndDietType.collect { values ->
                mealType = values.selectedMealType
                dietType = values.selectedDietType
            }
        }

        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
    fun applySearchQueries(searchQuery:String): HashMap<String, String> {

        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH]=searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }


    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(),
                "No internet connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else {
            if (backOnline) {
                Toast.makeText(getApplication(),
                    "We're back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }


    private fun saveBackOnline(backOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }
}