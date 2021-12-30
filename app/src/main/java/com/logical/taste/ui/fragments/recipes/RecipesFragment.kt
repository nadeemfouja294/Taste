package com.logical.taste.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.logical.taste.R
import com.logical.taste.adapter.RecipesAdapter
import com.logical.taste.databinding.FragmentRecipesBinding
import com.logical.taste.util.Constants.Companion.API_KEY
import com.logical.taste.util.NetworkResult
import com.logical.taste.util.observeOnce
import com.logical.taste.viewmodels.MainViewModel
import com.logical.taste.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

   val TAG="RecipesFragment"
    private var _binding:FragmentRecipesBinding?=null
    private val binding get() = _binding!!
    private  val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel:MainViewModel
    private lateinit var recipesViewModel:RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel

        setupRecycleView()
        readDatabase()

        binding.recipesFab.setOnClickListener{
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }
        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner,{database->
                if(database.isNotEmpty()){
                    Log.i(TAG,"readDatabase is called")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData(){
        Log.i(TAG,"requestApiData is called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner,{response->
         when(response) {
             is NetworkResult.Success->{
               hideShimmerEffect()
                response.data?.let { mAdapter.setData(it) }
             }
             is NetworkResult.Error->{
                 loadDataFromCache()
                 hideShimmerEffect()
                 Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
             }
             is NetworkResult.Loading->{
                 showShimmerEffect()
             }
         }
        })
    }

    private fun loadDataFromCache() {
       lifecycleScope.launch {
           mainViewModel.readRecipes.observe(viewLifecycleOwner,{database->
               if(database.isNotEmpty())
                   mAdapter.setData(database[0].foodRecipe)

           })
       }
    }


    private fun setupRecycleView() {
        binding.recycleView.adapter=mAdapter
        binding.recycleView.layoutManager= LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.recycleView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recycleView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}