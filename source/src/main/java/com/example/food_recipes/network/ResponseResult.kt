package com.example.food_recipes.network

sealed class ResponseResult<Type> (
    val result: Type? = null,
    val message: String? = null
) {
    /**
     * Loading class
     */
    class Loading<Type>: ResponseResult<Type>()

    /**
     * Success class
     */
    class Success<Type>(result: Type?): ResponseResult<Type>(result)

    /**
     * Error class
     */
    class Error<Type>(message: String?, result: Type? = null): ResponseResult<Type>(result, message)
}