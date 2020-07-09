package com.davidhsu.newssideproject.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.davidhsu.newssideproject.api.model.Article
import com.davidhsu.newssideproject.epoxy.model.NewsItemHolder_
import com.davidhsu.newssideproject.epoxy.model.newsItemHolder
import kotlinx.android.synthetic.main.fragment_news_recycler_item.view.*


/**
 * author: David.hsu
 * created on: 2020/7/1
 * description:
 */
class NewsController: EpoxyController() {

    private var newsDataList: List<Article>  = emptyList()

    fun setData(data: List<Article>) {
        newsDataList = data
        requestModelBuild()
    }

    override fun buildModels() {
        Log.e("test1234", "newsDataList size = ${newsDataList.size}")
        for (data in newsDataList) {
            newsItemHolder {
                id(newsDataList.indexOf(data))
                val title = data.author
                if (title.isNullOrEmpty()){
                    form("無")
                } else {
                    form(title)
                }
                val content = data.title
                if (title.isNullOrEmpty()){
                    content("無")
                } else {
                    content(content)
                }

                publishedAt(data.publishedAt)
                imgUrl(data.urlToImage)
            }
        }
    }
}