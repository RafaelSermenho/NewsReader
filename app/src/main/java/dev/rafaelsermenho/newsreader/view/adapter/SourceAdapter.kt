package dev.rafaelsermenho.newsreader.view.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import dev.rafaelsermenho.newsreader.R
import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.view.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.source_item.view.*

class SourceAdapter :
    RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    private var sourceList: SourceList? = null
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var cardView: CardView

    fun setData(sourceList: SourceList) {
        this.sourceList = sourceList
        notifyDataSetChanged()
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {

        cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.source_item, parent, false) as CardView
        return SourceViewHolder(cardView)
    }


    override fun getItemCount(): Int {
        return sourceList?.sources?.size ?: 0
    }

    override fun onBindViewHolder(sourceViewHolder: SourceViewHolder, position: Int) {
        val source = sourceList!!.sources[position]

        sourceViewHolder.sourceTitle.text = "${source.name}"
        sourceViewHolder.sourceDescription.text = "${source.description}"
        sourceViewHolder.sourceUrl.text = "${source.url}"
        sourceViewHolder.cardView.setOnClickListener {
            onItemClickListener.onItemClick(source)
        }
    }

    class SourceViewHolder(val cardView: CardView) : ViewHolder(cardView) {
        val sourceTitle = cardView.txtTitle!!
        val sourceDescription = cardView.txtDescription!!
        val sourceUrl = cardView.txtUrl!!

    }
}