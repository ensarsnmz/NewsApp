package com.example.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.RecyclerViewAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.service.NewsAPI
import com.example.newsapp.ui.DetailsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val BASE_URL = "https://newsapi.org/v2/"
    private lateinit var topHeadlinesNews: News
    //var topHeadlinesList: ArrayList<Article> = ArrayList()

    private var call: Call<News>? = null

    private var recyclerViewAdapter: RecyclerViewAdapter =
        RecyclerViewAdapter(topHeadlinesList, this@HomeFragment)


    private val binding get() = _binding!!

    companion object {
        var topHeadlinesList: ArrayList<Article> = ArrayList()
        val favoriteArticles = ArrayList<Article>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val cardViewManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recycler_view.layoutManager = cardViewManager

        if (call == null) {
            loadData("top-headlines")
        }

        search_button.setOnClickListener {
            if (et_search.text.toString().isEmpty()) {
                Toast.makeText(activity, "Please enter a keyword", Toast.LENGTH_LONG).show()
            } else {
                loadData(et_search.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(keyword: String) {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(NewsAPI::class.java)
        val api = resources.getString(R.string.api_key)


        if (keyword == "top-headlines") {
            call = service.getTopHeadlines("tr", api)
        } else {
            call = service.getSearchResults(keyword, api)
        }
        if (call != null) {
            call!!.enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            topHeadlinesNews = it
                            topHeadlinesList = topHeadlinesNews.articles
                            recyclerViewAdapter =
                                RecyclerViewAdapter(topHeadlinesList, this@HomeFragment)
                            recycler_view.adapter = recyclerViewAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)

        intent.putExtra("title", topHeadlinesList[position].title)
        intent.putExtra("image", topHeadlinesList[position].urlToImage)
        intent.putExtra("content", topHeadlinesList[position].content)
        intent.putExtra("date", topHeadlinesList[position].publishedAt)
        intent.putExtra("author", topHeadlinesList[position].author)
        intent.putExtra("url", topHeadlinesList[position].url)
        intent.putExtra("position", position)
        startActivity(intent)
    }

}