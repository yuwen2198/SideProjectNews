package com.davidhsu.newssideproject.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.sharedpreferences.AccountSharePreference
import com.davidhsu.newssideproject.view.activity.LogInActivity
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.fragment_about_recycler_item.view.*

/**
 * @author : david.hsu on 2019/9/2
 */
class AboutRecyclerViewAdapter(
    private val activity: Activity, private val logInType: String, private var aboutList: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val sharedPreference by lazy {
        AccountSharePreference(activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fragment_about_recycler_item, parent, false)
        return AboutRecycleViewViewHolder(view)
    }

    override fun getItemCount(): Int = aboutList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.item_text.text = aboutList[position]
        if (position == 1) {
            holder.itemView.itemRootView.setOnClickListener {
                logOut()
            }
        }
    }

    private fun logOut() {
        if(logInType == "facebook") {
            LoginManager.getInstance().logOut()
        }
        sharedPreference.clear()

        val intent = Intent(activity, LogInActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }

    inner class AboutRecycleViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}