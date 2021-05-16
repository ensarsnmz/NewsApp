package com.example.newsapp.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.ui.home.HomeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_view.*


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_view)

        av_title.text = intent.getStringExtra("title")
        Picasso.get().load(intent.getStringExtra("image"))
            .fit().into(av_article_image)
        av_author_text.text = intent.getStringExtra("author")
        av_date_text.text = intent.getStringExtra("date")!!.substring(0..9)
        av_content.text = intent.getStringExtra("content")

        val url = intent.getStringExtra("url")
        news_source_button.setOnClickListener {
            val intent2 = Intent(this, WebViewActivity::class.java)
            intent2.putExtra("url", url)
            startActivity(intent2)
        }

        av_back_button.setOnClickListener {
            onBackPressed()
        }

        av_share_button.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }

        val position = intent.getIntExtra("position", 0)
        av_favorite_button.setOnClickListener {
            HomeFragment.favoriteArticles.add(HomeFragment.topHeadlinesList[position])
            Toast.makeText(this, "Article added to favorites", Toast.LENGTH_SHORT).show()
        }
    }
}