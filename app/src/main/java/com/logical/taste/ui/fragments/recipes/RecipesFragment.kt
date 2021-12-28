package com.logical.taste.ui.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.logical.taste.R
import com.logical.taste.adapter.RecipesAdapter
import com.logical.taste.util.Constants.Companion.API_KEY
import com.logical.taste.util.NetworkResult
import com.logical.taste.viewmodels.MainViewModel
import com.logical.taste.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@AndroidEntryPoint
class RecipesFragment : Fragment() {


    private lateinit var mView: View
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
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        setupRecycleView()
        requestApiData()
        return mView
    }

    private fun requestApiData(){
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner,{response->
         when(response) {
             is NetworkResult.Success->{
               hideShimmerEffect()
                response.data?.let { mAdapter.setData(it) }
             }
             is NetworkResult.Error->{
                 hideShimmerEffect()
                 Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
             }
             is NetworkResult.Loading->{
                 showShimmerEffect()
             }
         }
        })
    }



    private fun setupRecycleView() {
        mView.recycleView.adapter=mAdapter
        mView.recycleView.layoutManager= LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        mView.recycleView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recycleView.hideShimmer()
    }


}