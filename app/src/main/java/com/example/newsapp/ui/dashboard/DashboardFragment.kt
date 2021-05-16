package com.example.newsapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.RecyclerViewAdapter
import com.example.newsapp.databinding.FragmentDashboardBinding
import com.example.newsapp.model.Article
import com.example.newsapp.ui.DetailsActivity
import com.example.newsapp.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*

class DashboardFragment : Fragment(), RecyclerViewAdapter.OnItemClickListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private var favoriteNewsList: ArrayList<Article> = ArrayList()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val cardViewManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        favorite_view.layoutManager = cardViewManager

        favoriteNewsList = HomeFragment.favoriteArticles
        recyclerViewAdapter = RecyclerViewAdapter(favoriteNewsList, this@DashboardFragment)
        favorite_view.adapter = recyclerViewAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, DetailsActivity::class.java)

        intent.putExtra("title", favoriteNewsList[position].title)
        intent.putExtra("image", favoriteNewsList[position].urlToImage)
        intent.putExtra("content", favoriteNewsList[position].content)
        intent.putExtra("date", favoriteNewsList[position].publishedAt)
        intent.putExtra("author", favoriteNewsList[position].author)
        intent.putExtra("url", favoriteNewsList[position].url)

        startActivity(intent)
    }

}