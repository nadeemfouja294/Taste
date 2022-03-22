package com.logical.taste.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.logical.taste.R
import com.logical.taste.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.logical.taste.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.logical.taste.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import java.lang.Exception
import java.util.*

class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,{value->
            mealTypeChip=value.selectedMealType
            mealTypeChipId=value.selectedMealTypeId
            updateChip(value.selectedMealTypeId,mView.mealType_chipGroup)
            updateChip(value.selectedDietTypeId,mView.dietType_chipGroup)

        })


        mView.mealType_chipGroup.setOnCheckedChangeListener{group,selectedChipId->
             val chip=group.findViewById<Chip>(selectedChipId)
             val selectedMealType=chip.text.toString().lowercase(Locale.ROOT)
             mealTypeChip=selectedMealType
             mealTypeChipId=selectedChipId

         }
        mView.dietType_chipGroup.setOnCheckedChangeListener{group,selectedChipId->
            val chip=group.findViewById<Chip>(selectedChipId)
            val selectedDietType=chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip=selectedDietType
           dietTypeChipId=selectedChipId

        }
        mView.apply_btn.setOnClickListener{
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,mealTypeChipId,dietTypeChip,dietTypeChipId
            )
            val action=RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return mView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
       if(chipId!=0) {
           try {
               val chip = chipGroup.findViewById<Chip>(chipId)
               chip.isChecked = true
           }catch (e:Exception) {
               Log.i("RecipesBottomSheet","${e.message.toString()}")
           }
       }

    }


}