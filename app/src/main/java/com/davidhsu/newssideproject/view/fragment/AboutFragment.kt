package com.davidhsu.newssideproject.view.fragment

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.AboutRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_about.view.*

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class AboutFragment : Fragment() {

    private var logInType = ""
    private var name = ""
    private var photo = ""
    private var aboutList: MutableList<String> = ArrayList()

    private val aboutFragmentAdapter by lazy {
        aboutList.apply {
            add("關於懶人新聞")
            add("登出")
        }
        AboutRecyclerViewAdapter(activity, logInType, aboutList)
    }

    override fun onStart() {
        super.onStart()
        if (isAdded) {
            logInType = arguments?.getString("loginType").toString()
            name = arguments?.getString("name").toString()
            photo = arguments?.getString("photoUrl").toString()

            Glide.with(context).load(photo).into(profileImage)
            nameText.text = name
        }
    }

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false).apply {
            aboutRecyclerView.layoutManager = LinearLayoutManager(activity)
            aboutRecyclerView.adapter = aboutFragmentAdapter
        }
    }
}