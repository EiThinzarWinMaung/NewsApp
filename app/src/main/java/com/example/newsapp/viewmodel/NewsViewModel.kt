package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.api.ApiClient
import com.example.newsapp.model.News
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel: ViewModel() {
    private var result:MutableLiveData<News> = MutableLiveData()
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var errorStatus: MutableLiveData<Boolean> = MutableLiveData()
    private var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getResult(): LiveData<News> = result

    fun getErrorMessage(): LiveData<String> = errorMessage
    fun getErrorStatus(): LiveData<Boolean> = errorStatus
    fun getLoading(): LiveData<Boolean> = loading

    fun loadResult() {
        var apiClient = ApiClient()
        var apiCall = apiClient.getTopHeadlines()

        apiCall.enqueue(object: Callback<News> {                // call class object "News"

            // Failure
            override fun onFailure(call: Call<News>, t: Throwable) {
                loading.value = false
                errorStatus.value = true
                errorMessage.value = t.toString()
            }

            // Success
            override fun onResponse(call: Call<News>, response: Response<News>) {
                loading.value = false
                errorStatus.value = false
                result.value = response.body()
            }
        })
    }
}
