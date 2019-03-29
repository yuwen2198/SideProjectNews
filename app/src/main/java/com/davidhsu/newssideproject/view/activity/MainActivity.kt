package com.davidhsu.newssideproject.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.MainActivityViewPagerAdapter
import com.davidhsu.newssideproject.view.fragment.AboutFragment
import com.davidhsu.newssideproject.view.fragment.NewsFragment
import com.davidhsu.newssideproject.view.fragment.WeatherFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {
        MainActivityViewPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        initStatusBar()
        initViewPager()
        initListener()
    }

    private fun initStatusBar() = window.run {
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //设置状态栏颜色
        statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.black)
    }

    private fun initViewPager() {
        adapter.addFragment(NewsFragment())
        adapter.addFragment(WeatherFragment())
        adapter.addFragment(AboutFragment())
        viewpager.adapter = adapter
    }

    private fun initListener() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewpager.addOnPageChangeListener(onPageChangeListener)
    }

    private val onPageChangeListener = (object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            navigation.menu.getItem(position).isChecked = true
        }

        override fun onPageSelected(position: Int) {

        }

    })

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val itemID = item.itemId
        when (itemID) {
            R.id.navigation_home -> {
                viewpager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewpager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewpager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}