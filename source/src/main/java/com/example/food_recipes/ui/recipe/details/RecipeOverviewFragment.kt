package com.example.food_recipes.ui.recipe.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.food_recipes.R
import com.example.food_recipes.core.adapters.IngredientsAdapter
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.databinding.FragmentRecipeOverviewBinding
import com.example.food_recipes.misc.APIConstants.Companion.RECIPE_DETAILS_BUNDLE

class RecipeOverviewFragment : Fragment() {

    private var _binding: FragmentRecipeOverviewBinding? = null
    private val binding get() = _binding!!

    private val ingredientsAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeOverviewBinding.inflate(inflater, container, false)

        dataBinding()
        setupRecyclerView()

        return  binding.root
    }

    private fun setupRecyclerView() {
        binding.ingredientRecyclerView.adapter = ingredientsAdapter
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun dataBinding() {
        val args = arguments
        val data: Recipe = args!!.getParcelable<Recipe>(RECIPE_DETAILS_BUNDLE) as Recipe

        binding.recipeBannerImageView.load(data.image)
        binding.recipeTitleTextView.text = data.title
        binding.recipeMinutesOverview.text = data.readyInMinutes.toString()
        binding.recipeLikesOverview.text = data.aggregateLikes.toString()
        binding.recipeScoreOverview.text = data.healthScore.toString()
        binding.recipeDescTextView.text = HtmlCompat.fromHtml(
            data.summary,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()

        val myBundle: Recipe? = args?.getParcelable(RECIPE_DETAILS_BUNDLE)
        myBundle?.extendedIngredients?.let { ingredientsAdapter.setIngredients(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}