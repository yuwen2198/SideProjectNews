package com.davidhsu.newssideproject.view.fragment

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.davidhsu.newssideproject.R
//import kotlinx.android.synthetic.main.fragment_about.*


/**
 *
 * @author : DavidHsu on 2019/03/28
 *
 */
class AboutFragment : Fragment() {

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }



}