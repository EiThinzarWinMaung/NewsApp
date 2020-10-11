package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.toSimpleString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.item_top_headlines.view.*

class TopHeadlinesAdapter(var topheadlinesList: ArrayList<Article> = ArrayList()) :
    RecyclerView.Adapter<TopHeadlinesAdapter.TopHeadlinesViewHolder>() {

    var mClickListerner: ClickListerner? = null

    fun setOnClickListener(clickListener: ClickListerner) {
        this.mClickListerner = clickListener
    }

    inner class TopHeadlinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var article: Article
        private var view: View = itemView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: Article) {
            this.article = article
            itemView.txtName.text = article.source.name
            itemView.txtAuthor.text = article.author
            itemView.txtTitle.text = article.title
            itemView.txtDescription.text = article.description
            itemView.txtPublishedAt.text = toSimpleString(article.publishedAt)
            Picasso.get().load(article.urlToImage).into(itemView.imgUrlImage)
        }

        override fun onClick(view: View?) {
            mClickListerner?.onclick(article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlinesViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_headlines, parent, false)
        return TopHeadlinesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topheadlinesList.size
    }

    override fun onBindViewHolder(holder: TopHeadlinesViewHolder, position: Int) {
        return holder.bind(topheadlinesList[position])
    }

    fun updateArticle(topheadlinesList: ArrayList<Article>) {
        this.topheadlinesList = topheadlinesList
        notifyDataSetChanged()
    }

    interface ClickListerner {
        fun onclick(article: Article)
    }
}