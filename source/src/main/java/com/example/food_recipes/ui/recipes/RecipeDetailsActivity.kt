package com.example.food_recipes.ui.recipes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.food_recipes.R
import com.example.food_recipes.core.adapters.PagerAdapter
import com.example.food_recipes.core.data.local.database.entities.SavedRecipesEntity
import com.example.food_recipes.databinding.ActivityRecipeDetailsBinding
import com.example.food_recipes.misc.APIConstants.Companion.RECIPE_DETAILS_BUNDLE
import com.example.food_recipes.ui.recipe.details.RecipeInstructionsFragment
import com.example.food_recipes.ui.recipe.details.RecipeOverviewFragment
import com.example.food_recipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val args by navArgs<RecipeDetailsActivityArgs>()
    private lateinit var savedIcon: MenuItem
    private var savedRecipe = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        setupPager()
    }

    private fun setupPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(RecipeOverviewFragment())
        fragments.add(RecipeInstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_DETAILS_BUNDLE, args.recipe)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.recipeDetailsViewPager.isUserInputEnabled = false
        binding.recipeDetailsViewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(
            binding.recipeDetailsTabLayout,
            binding.recipeDetailsViewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recpie_details_menu, menu)
        savedIcon = menu!!.findItem(R.id.recipeSaveMenu)
        isSavedRecipes()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        else if (item.itemId == R.id.recipeSaveMenu && !savedRecipe)
            saveRecipe()
        else if (item.itemId == R.id.recipeSaveMenu && savedRecipe)
            unSaveRecipe()

        return super.onOptionsItemSelected(item)
    }

    private fun isSavedRecipes() {
        mainViewModel.readSavedRecipes.observe(this) { recipes ->
            try {
                for (recipe in recipes)
                    if (recipe.recipe.recipeId == args.recipe.recipeId) {
                        savedRecipeId = recipe.id
                        savedRecipe = true
                        enabledSavedIcon(true)
                    }
            } catch (e: Exception) {
            }
        }
    }

    private fun saveRecipe() {
        mainViewModel.insertSavedRecipe(
            SavedRecipesEntity(
                0,
                args.recipe
            )
        )
        enabledSavedIcon(true)
        showSnackBar("Recipe saved successfully")
        savedRecipe = true
    }

    private fun unSaveRecipe() {
        mainViewModel.deleteSavedRecipe(
            SavedRecipesEntity(
                savedRecipeId,
                args.recipe
            )
        )
        enabledSavedIcon(false)
        showSnackBar("Recipe unsaved successfully")
        savedRecipe = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.recipeDetailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("OK") {}
            .show()
    }

    private fun enabledSavedIcon(enable: Boolean) {
        if (enable)
            savedIcon.icon = ContextCompat.getDrawable(this, R.drawable.ic_round_bookmark_24)
        else
            savedIcon.icon = ContextCompat.getDrawable(this, R.drawable.ic_outline_save)

        savedIcon.icon?.setTint(ContextCompat.getColor(this, R.color.white))
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.recipeDetailsToolBar)
        binding.recipeDetailsToolBar.setTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.recipeDetailsToolBar.title = "Recipe Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        enabledSavedIcon(false)
    }
}