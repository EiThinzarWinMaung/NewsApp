package com.example.newsapp.api

import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val BASE_URL = "https://newsapi.org/v2/"

//  var retrofit: Retrofit? = null
    var apiInterface: NewsApiInterface

    init {
        var retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface = retrofit.create(NewsApiInterface::class.java)
    }

    fun getTopHeadlines(): Call<News> {
        return apiInterface.getTopHeadlines("us", "b90dd3c699c94e97944d7cc600740c26")
    }
}