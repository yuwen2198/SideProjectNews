package com.davidhsu.newssideproject.adapter

import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.api.model.Article
import com.davidhsu.newssideproject.utils.LogUtil
import kotlinx.android.synthetic.main.recycleview_news_item.view.*

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class RecycleViewAdapter(private var items: List<Article>, private val context: FragmentActivity?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER_TYPE = 0
        const val NEWS_TYPE = 1

        const val HEADER_COUNT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == NEWS_TYPE) {
            val view = layoutInflater.inflate(R.layout.recycleview_news_item, parent, false)
            RecycleViewViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.recycleview_header, parent, false)
            HeaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecycleViewViewHolder) {
            newsView(holder , position - 1)
        } else {
            //TODO HEADER
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        HEADER_TYPE
    } else {
        NEWS_TYPE
    }

    private fun newsView(holder: RecycleViewViewHolder, position: Int) {
        val data = items[position]

        val title = data.author
        if (title.isNullOrEmpty()){
            holder.itemView.news_form.text = "無"
        } else {
            holder.itemView.news_form.text = title
        }

        val imgUrl = data.urlToImage
        Glide.with(context!!).load(imgUrl).centerCrop().fitCenter().into(holder.itemView.news_img)

        holder.itemView.new_content.text = data.title
        holder.itemView.news_publishedAt.text = data.publishedAt
        holder.itemView.newsRootView.setOnClickListener { intentNews(data) }
    }

    private fun intentNews(data : Article) {
        val uri = Uri.parse(data.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context!!.startActivity(intent)
    }

    override fun getItemCount(): Int = items.size + HEADER_COUNT

    fun setData (data: List<Article>) {
        items = data
        notifyDataSetChanged()
    }

    inner class RecycleViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class HeaderViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
}
