package com.davidhsu.newssideproject.view.fragment

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.AboutRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_about.view.*

/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class AboutFragment : Fragment() {

    private var logInType = String()
    private var name = String()
    private var photo = String()
    private var aboutList: MutableList<String> = ArrayList()

    private lateinit var baseView: View

    private val aboutFragmentAdapter by lazy {
        aboutList.apply {
            add("關於懶人新聞")
            add("登出")
        }
        AboutRecyclerViewAdapter(activity!!, logInType, aboutList)
    }

    override fun onStart() {
        super.onStart()
        if (isAdded) {
            logInType = arguments?.getString("loginType").toString()
            name = arguments?.getString("name").toString()
            photo = arguments?.getString("photoUrl").toString()

            Glide.with(context).load(photo).into(baseView.profileImage)
            baseView.nameText.text = name
        }
    }

    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        baseView = inflater.inflate(R.layout.fragment_about, container, false)
        baseView.aboutRecyclerView.apply {
            aboutRecyclerView.layoutManager = LinearLayoutManager(activity)
            aboutRecyclerView.adapter = aboutFragmentAdapter
        }
        return baseView
    }
}