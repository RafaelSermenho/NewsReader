package dev.rafaelsermenho.newsreader.view.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dev.rafaelsermenho.newsreader.R
import dev.rafaelsermenho.newsreader.model.Article
import kotlinx.android.synthetic.main.common_news_item.view.*


class ArticleAdapter :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articleList: ArrayList<Article> = ArrayList()
    private lateinit var cardView: CardView
    private lateinit var context: Context
    private val headline = 0
    private val commonNews = 1

    fun setData(articleList: List<Article>) {
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        context = parent.context

        when (viewType) {
            headline -> cardView = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.headline_item
                    , parent, false
                ) as CardView

            commonNews -> cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.common_news_item, parent, false) as CardView
        }

        return ArticleViewHolder(cardView)
    }


    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(articleViewHolder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        articleViewHolder.title.text = "${article.title}"
        articleViewHolder.description.text = "${article.description}"
        articleViewHolder.author.text = "${article.author}"
        articleViewHolder.dtPublished.text = "${article.publishedAt}"
        Glide.with(context)
            .load(article.urlToImage)
            .transform(CenterCrop(), RoundedCorners(16))
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_camera)
            .fallback(R.drawable.ic_camera)
            .into(articleViewHolder.imgNews)
    }

    class ArticleViewHolder(cardView: CardView) : ViewHolder(cardView) {
        val title = cardView.txtTitle!!
        val description = cardView.txtDescription!!
        val author = cardView.txtAuthor!!
        val dtPublished = cardView.txtPublishedAt!!
        val imgNews = cardView.imgNews!!
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> headline
            else -> commonNews
        }
    }
}