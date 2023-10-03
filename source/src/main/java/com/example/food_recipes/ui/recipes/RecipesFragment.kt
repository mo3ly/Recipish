package com.example.food_recipes.ui.recipes

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_recipes.R
import com.example.food_recipes.core.adapters.RecipesAdapter
import com.example.food_recipes.databinding.FragmentRecipesBinding
import com.example.food_recipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null

    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var recipesViewModel: RecipesViewModel

    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(this)[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        searchBarListener()
        retrieveData()
        handleSearchRequestFromHome()

        binding.recipesFab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_recipes_to_recipesDialogSheet)
        }

        return binding.root
    }


    private fun setupRecyclerView() {
        binding.recipesRecyclerView.adapter = recipesAdapter
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadingEffect(true)
    }

    private fun handleSearchRequestFromHome() {
        if (args.searchValueFromHome != "") {
            binding.recipesSearchTextInput.setText(args.searchValueFromHome)
            recipesViewModel.searchRequest(
                args.searchValueFromHome,
                mainViewModel,
                recipesAdapter,
                this@RecipesFragment
            )
        }
    }

    private fun searchBarListener() {
        binding.recipesSearchTextInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                recipesViewModel.searchRequest(
                    binding.recipesSearchTextInput.text.toString(),
                    mainViewModel,
                    recipesAdapter,
                    this@RecipesFragment
                )

            false
        }
    }

    private fun retrieveData() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { db ->
                if (db.isNotEmpty() && !args.returnFromRecipeOptionsDialog) {
                    recipesAdapter.setRecipes(db.first().recipes)
                    loadingEffect(false)
                } else
                    recipesViewModel.recipesRequest(
                        mainViewModel,
                        recipesAdapter,
                        this@RecipesFragment
                    )

            }
        }
    }

    fun loadingEffect(show: Boolean) {
        if (show) {
            binding.shimmerFrameLayout.startShimmer()
            binding.shimmerFrameLayout.visibility = View.VISIBLE
            binding.recipesRecyclerView.visibility = View.GONE
        } else {
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.recipesRecyclerView.visibility = View.VISIBLE
        }
    }

    fun showSnackBar(message: String) {
        Snackbar.make(
            binding.recipesLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("OK") {}
            .show()
    }

    /**
     * https://stackoverflow.com/questions/47854598/livedata-remove-observer-after-first-callback
     */
    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                removeObserver(this)
                observer.onChanged(t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}