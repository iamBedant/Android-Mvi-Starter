package com.iambedant.mvistarter.feature.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.iambedant.mvistarter.R
import android.view.View
import com.bumptech.glide.Glide
import com.iambedant.mvistarter.data.remote.model.ArticlesItem
import kotlinx.android.synthetic.main.rv_item_news.view.*

/**
 * Created by @iamBedant on 25/05/18.
 */


class NewsAdapter(val dataSource: List<ArticlesItem>, private val share:(ArticlesItem)->Unit) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_news, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(dataSource[position]){
            holder.headline?.text = title
            holder.description.text = description
            holder.time.text = publishedAt
            Glide.with(holder.coverImage.context).load(urlToImage).into(holder.coverImage)
            holder.item.setOnClickListener { share.invoke(this) }
        }

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item = view
    val headline = view.headlineTv
    val coverImage= view.coverImage
    val time = view.dateTv
    val description= view.descriptionTv
}