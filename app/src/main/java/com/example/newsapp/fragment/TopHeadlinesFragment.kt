package com.example.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.TopHeadlinesAdapter
import com.example.newsapp.api.ApiClient
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopHeadlinesFragment : Fragment(), TopHeadlinesAdapter.ClickListerner {

    lateinit var newsViewModel: NewsViewModel
    lateinit var topHeadlinesAdapter: TopHeadlinesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topHeadlinesAdapter = TopHeadlinesAdapter()
        topHeadlinesAdapter.setOnClickListener(this)

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
//      newsViewModel.loadResult()

        TopHeadlinesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topHeadlinesAdapter
        }
        observeViewmodel()
    }

    fun observeViewmodel() {
        newsViewModel.getResult().observe(viewLifecycleOwner, Observer<News> { news ->
            topHeadlinesAdapter.updateArticle(news.articles as ArrayList<Article>)
        })

        newsViewModel.getLoading().observe(viewLifecycleOwner, Observer { isLoading ->
            loadingBar.visibility =
            if (isLoading) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        })

        newsViewModel.getErrorStatus().observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                newsViewModel.getErrorMessage().observe(viewLifecycleOwner, Observer { message ->
                    txtErrorMsg.text = message
                })
            }
        })
    }

    override fun onResume() {
        super.onResume()
        newsViewModel.loadResult()
    }

    override fun onclick(article: Article) {
        var action = TopHeadlinesFragmentDirections.actionTopHeadlinesFragmentToDetailsFragment(article.url)
        findNavController().navigate(action)

    }
}