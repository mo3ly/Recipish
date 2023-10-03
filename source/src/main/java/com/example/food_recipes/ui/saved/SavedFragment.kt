package com.example.food_recipes.ui.saved

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_recipes.core.adapters.SavedRecipesAdapter
import com.example.food_recipes.databinding.FragmentSavedBinding
import com.example.food_recipes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val savedRecipesAdapter by lazy {
        SavedRecipesAdapter()
    }

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.savedRecipesAdapter = savedRecipesAdapter
        binding.mainViewModel = mainViewModel

        setupRecyclerView(binding.savedRecipesRecyclerView)
        deleteButtonListener()

        val root: View = binding.root

        return root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = savedRecipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun deleteButtonListener() {
        binding.savedRecipesDeleteFab.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(
                        "Delete"
                    ) { _, _ -> mainViewModel.deleteAllSavedRecipes() }
                    setNegativeButton(
                        "Cancel"
                    ) { _, _ -> }
                }
                builder?.setMessage("Are you sure about deleting all the saved recipes?")
                    ?.setTitle("Delete saved recipes")
                builder.create()
            }
            alertDialog?.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}