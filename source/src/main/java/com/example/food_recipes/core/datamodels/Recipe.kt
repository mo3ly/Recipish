package com.example.food_recipes.core.datamodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
//    @SerializedName("instructions")
//    val instructions: String = "",
    @SerializedName("id")
    val recipeId: Int = 0,
    @SerializedName("healthScore")
    val healthScore: Int? = 0,
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int = 0,
    @SerializedName("cheap")
    val cheap: Boolean = false,
    @SerializedName("dairyFree")
    val dairyFree: Boolean= false,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient> = emptyList(),
    @SerializedName("glutenFree")
    val glutenFree: Boolean = false,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("sourceName")
    val sourceName: String = "",
    @SerializedName("sourceUrl")
    val sourceUrl: String = "",
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("vegan")
    val vegan: Boolean = false,
    @SerializedName("vegetarian")
    val vegetarian: Boolean= false,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean= false,
    @SerializedName("veryPopular")
    val veryPopular: Boolean= false,
): Parcelable