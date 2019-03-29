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
import kotlinx.android.synthetic.main.item_recycleview.view.*

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class RecycleViewAdapter(private var items: List<Article>?, private val context: FragmentActivity?) : RecyclerView.Adapter<RecycleViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recycleview, parent, false)
        return RecycleViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecycleViewViewHolder, position: Int) {
        val data = items!![position]

        val title = data.author
        if (title.isNullOrEmpty()){
            holder.tvNewsForm.text = "無"
        } else {
            holder.tvNewsForm.text = title
        }

        val imgUrl = data.urlToImage
        Glide.with(context!!).load(imgUrl).centerCrop().fitCenter().into(holder.imgNews)

        holder.tvNewsContent.text = data.title

        holder.tvPublishAt.text = data.publishedAt

        holder.rootView.setOnClickListener { intentNews(data) }
    }

    private fun intentNews(data : Article) {
        val uri = Uri.parse(data.url)
        val intent = Intent(Intent.ACTION_VIEW,uri)
        context!!.startActivity(intent)
    }

    override fun getItemCount(): Int = items!!.size

    fun setData (data: List<Article>) {
        items = data
        notifyDataSetChanged()
        LogUtil.d("data = $data")
    }

}

class RecycleViewViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val tvNewsForm = view.news_form!!
    val tvNewsContent = view.new_content!!
    val imgNews = view.news_img!!
    val tvPublishAt = view.news_publishedAt!!
    val rootView = view.newsRootView!!

}