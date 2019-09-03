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
import kotlinx.android.synthetic.main.fragment_news_recycler_header.view.*
import kotlinx.android.synthetic.main.fragment_news_recycler_item.view.*

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class NewsRecycleViewAdapter(private var items: List<Article>, private val context: FragmentActivity?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEADER_TYPE = 0
        const val NEWS_TYPE = 1

        const val HEADER_COUNT = 1
    }

    private var userMail = ""
    private var name = ""
    private var photoUrl = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == NEWS_TYPE) {
            val view = layoutInflater.inflate(R.layout.fragment_news_recycler_item, parent, false)
            RecycleViewViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.fragment_news_recycler_header, parent, false)
            HeaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecycleViewViewHolder) {
            newsView(holder , position - 1)
        } else if (holder is HeaderViewHolder){
            setHeaderView(holder)
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        HEADER_TYPE
    } else {
        NEWS_TYPE
    }

    private fun setHeaderView(holder: HeaderViewHolder) {
        holder.itemView.nameText.text = name
        holder.itemView.idText.text = userMail
        holder.itemView.idText.requestFocus()
        Glide.with(context).load(photoUrl).into(holder.itemView.profile_image)
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
        Glide.with(context).load(imgUrl).centerCrop().fitCenter().into(holder.itemView.news_img)

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

    fun setUserInfo(userMail: String, name: String, photoUrl: String) {
        this.userMail = userMail
        this.name = name
        this.photoUrl = photoUrl
        notifyDataSetChanged()
    }

    inner class RecycleViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class HeaderViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
}
