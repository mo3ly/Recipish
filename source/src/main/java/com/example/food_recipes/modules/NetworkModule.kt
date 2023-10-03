package com.example.food_recipes.modules

import com.example.food_recipes.core.data.remote.api.SpoonacularAPI
import com.example.food_recipes.misc.Config.Companion.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * provide the retrofit library with the Ok Http Client
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    /**
     * provide the retrofit library with the Gson Convertor Factory
     */
    @Singleton
    @Provides
    fun provideGsonConvertorFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * Because the retrofit library is external we have to instantiate it to use it with di (dagger)
     */
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        httpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(httpClient)
            .addConverterFactory(gsonFactory)
            .build()
    }

    /**
     * provide the retrofit api service based on the spoonacular api
     */
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): SpoonacularAPI {
        return retrofit.create(SpoonacularAPI::class.java)
    }
}