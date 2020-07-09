package com.davidhsu.newssideproject.epoxy.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.*
import com.bumptech.glide.Glide
import com.davidhsu.newssideproject.R
import kotlinx.android.synthetic.main.fragment_news_recycler_item.view.*

/**
 * author: David.hsu
 * created on: 2020/7/2
 * description:
 */

@EpoxyModelClass(layout = R.layout.fragment_news_recycler_item)
abstract class NewsItemHolder: EpoxyModelWithHolder<NewsItemHolder.ItemHolder>() {

    @EpoxyAttribute
    var form: String = ""

    @EpoxyAttribute
    var content: String = ""

    @EpoxyAttribute
    var publishedAt: String = ""

    @EpoxyAttribute
    var imgUrl: String = ""

    override fun bind(holder: ItemHolder) {
        holder.newsForm.text = form
        holder.newsContent.text = content
        holder.newsPublishedAt.text = publishedAt

        Glide.with(holder.newsImageView.context).load(imgUrl).centerCrop().fitCenter().into(holder.newsImageView)
    }

    inner class ItemHolder : EpoxyHolder(){

        lateinit var newsForm: TextView
        lateinit var newsContent: TextView
        lateinit var newsPublishedAt: TextView
        lateinit var newsImageView: ImageView

        override fun bindView(itemView: View) {
            newsForm = itemView.news_form
            newsContent = itemView.news_content
            newsPublishedAt = itemView.news_publishedAt
            newsImageView = itemView.news_img
        }
    }
}