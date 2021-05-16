package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*

class RecyclerViewAdapter(private val articleList: ArrayList<Article>,
                          private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.CardHolder>() {




    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        fun bind(article: Article){
            itemView.tv_title.text = article.title
            itemView.tv_description.text = article.description
            itemView.tv_date_and_source.text =
                "${article.publishedAt.substring(0..9)}    ${article.source.name}"
            Picasso.get().load(article.urlToImage)
                .centerCrop().fit().into(itemView.iv_article_image)
        }
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if(position!= RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return CardHolder(view)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }
}