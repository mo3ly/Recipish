package com.example.food_recipes.core.repositories

import com.example.food_recipes.core.data.local.LocalDataSource
import com.example.food_recipes.core.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}