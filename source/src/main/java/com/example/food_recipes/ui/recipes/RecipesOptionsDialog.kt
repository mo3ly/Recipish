package com.example.food_recipes.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.food_recipes.core.repositories.RecipeOptions
import com.example.food_recipes.databinding.RecipesOptionsDialogBinding
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_CUISINE
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_DIET
import com.example.food_recipes.misc.APIConstants.Companion.DEFAULT_TYPE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

/**
 * create an instance of this fragment.
 */
class RecipesOptionsDialog : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private var _binding: RecipesOptionsDialogBinding? = null
    private val binding get() = _binding!!

    private var defaultOptions = RecipeOptions(
        DEFAULT_CUISINE, 0, DEFAULT_TYPE, 0, DEFAULT_DIET, 0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = RecipesOptionsDialogBinding.inflate(inflater, container, false)

        restoreChipsStates()
        onChipsStateChange()
        onSubmitOptions()

        return binding.root
    }

    /***
     * On chips state change events listener for cuisine, meal type, and diet
     */
    private fun onChipsStateChange() {
        binding.cuisineChipsGroup.setOnCheckedStateChangeListener { group, selected ->
            val chip = group.findViewById<Chip>(selected.first())
            val selectedType = chip.text.toString().lowercase(Locale.ROOT)
            defaultOptions.Cuisine = selectedType
            defaultOptions.CuisineId = selected.first()
        }

        binding.mealTypeChipsGroup.setOnCheckedStateChangeListener { group, selected ->
            val chip = group.findViewById<Chip>(selected.first())
            val selectedType = chip.text.toString().lowercase(Locale.ROOT)
            defaultOptions.MealType = selectedType
            defaultOptions.MealTypeId = selected.first()
        }

        binding.dietTypeChipsGroup.setOnCheckedStateChangeListener { group, selected ->
            val chip = group.findViewById<Chip>(selected.first())
            val selectedType = chip.text.toString().lowercase(Locale.ROOT)
            defaultOptions.DietType = selectedType
            defaultOptions.DietTypeId = selected.first()
        }

    }

    /**
     * Restore the saved states of the chips
     */
    private fun restoreChipsStates() {
        recipesViewModel.savedRecipeOptions.asLiveData().observe(viewLifecycleOwner) { options ->
            defaultOptions.Cuisine = options.Cuisine
            activateChips(options.CuisineId, binding.cuisineChipsGroup)

            defaultOptions.MealType = options.MealType
            activateChips(options.MealTypeId, binding.mealTypeChipsGroup)

            defaultOptions.DietType = options.DietType
            activateChips(options.DietTypeId, binding.dietTypeChipsGroup)
        }
    }

    /**
     * On submitting the recipe options when the apply button is pressed
     */
    private fun onSubmitOptions() {
        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveRecipeOptions(
                defaultOptions.Cuisine,
                defaultOptions.CuisineId,
                defaultOptions.MealType,
                defaultOptions.MealTypeId,
                defaultOptions.DietType,
                defaultOptions.DietTypeId,
            )

            findNavController().navigate(
                RecipesOptionsDialogDirections.actionRecipesDialogSheetToNavigationRecipes(
                    true
                )
            )
        }
    }

    /**
     * Activate chip
     */
    private fun activateChips(chipId: Int, chipsGroup: ChipGroup) {
        if (chipId != 0)
            try {
                chipsGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (_: java.lang.Exception) {

            }
    }

}