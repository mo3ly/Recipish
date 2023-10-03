package com.example.food_recipes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_recipes.core.adapters.RecommendedRecipesAdapter
import com.example.food_recipes.databinding.FragmentHomeBinding
import com.example.food_recipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var homeViewModel: HomeViewModel
    private val recommendedRecipesAdapter by lazy { RecommendedRecipesAdapter() }

    var loaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupSearchBar()
        handleCategoreisClick()
        setupRecyclerView()

        homeViewModel.randomRecipeRequest(
            mainViewModel,
            recommendedRecipesAdapter,
            loaded,
            this
        )

        val root: View = binding.root
        return root
    }

    private fun handleCategoreisClick() {

        binding.mainCourseImageView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationRecipes(
                false,
                "main course"
            )
            findNavController().navigate(action)
        }
        binding.breakfastImageView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationRecipes(
                false,
                "breakfast"
            )
            findNavController().navigate(action)
        }
        binding.soupImageView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationRecipes(
                false,
                "soup"
            )
            findNavController().navigate(action)
        }
        binding.dessertImageView.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationRecipes(
                false,
                "dessert"
            )
            findNavController().navigate(action)
        }
    }

    private fun setupSearchBar() {
        binding.recipeSearchHomeTextView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationRecipes(
                    false,
                    binding.recipeSearchHomeTextView.text.toString()
                )
                findNavController().navigate(action)
            }
            false
        }
    }

    private fun setupRecyclerView() {
        binding.recommendedRecipesRecyclerView.adapter = recommendedRecipesAdapter
        binding.recommendedRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}