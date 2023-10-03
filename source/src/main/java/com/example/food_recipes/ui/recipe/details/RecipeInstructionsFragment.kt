package com.example.food_recipes.ui.recipe.details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.food_recipes.core.datamodels.Recipe
import com.example.food_recipes.databinding.FragmentRecipeInstructionsBinding
import com.example.food_recipes.misc.APIConstants


class RecipeInstructionsFragment : Fragment() {


    private var _binding: FragmentRecipeInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val bundle: Recipe =
            args!!.getParcelable<Recipe>(APIConstants.RECIPE_DETAILS_BUNDLE) as Recipe

        setupWebView(bundle)

        return binding.root
    }

    private fun setupWebView(bundle: Recipe) {
        binding.recipeInstructionsWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                view?.isVisible = false
                binding.notFoundTextView.isVisible = true
                binding.loadingImageView.isVisible = false
                binding.loadingTextView.isVisible = false

//                if (bundle.instructions.isNotEmpty())
//                    binding.notFoundTextView.text = HtmlCompat.fromHtml(
//                        bundle.instructions,
//                        HtmlCompat.FROM_HTML_MODE_LEGACY
//                    ).toString()

                super.onReceivedError(view, request, error)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                view?.isVisible = false
                binding.notFoundTextView.isVisible = false
                binding.loadingImageView.isVisible = true
                binding.loadingTextView.isVisible = true
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (view?.progress == 100) {
                    binding.notFoundTextView.isVisible = false
                    binding.loadingImageView.isVisible = false
                    binding.loadingTextView.isVisible = false
                    view?.isVisible = true
                }
                super.onPageFinished(view, url)
            }
        }

        val websiteUrl: String = bundle!!.sourceUrl
        binding.recipeInstructionsWebView.loadUrl(websiteUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}