package com.davidhsu.newssideproject.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.davidhsu.newssideproject.R
import com.davidhsu.newssideproject.adapter.MainActivityViewPagerAdapter
import com.davidhsu.newssideproject.view.fragment.AboutFragment
import com.davidhsu.newssideproject.view.fragment.NewsFragment
import com.davidhsu.newssideproject.view.fragment.WeatherFragment
import com.davidhsu.newssideproject.viewmodel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var userMail = String()
    private var name = String()
    private var photoUrl = String()
    private var currentLocation = String()
    private var logInType = String()

    private val adapter by lazy {
        MainActivityViewPagerAdapter(supportFragmentManager)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        viewModel.getCurrentLocation(this)

        getIntentData()
        initStatusBar()
        initListener()
        setObserver()
    }

    private fun setObserver() {
        viewModel.currentLocation.observe(this, Observer { currentLocation ->
            this.currentLocation = currentLocation.toString()
            initViewPager()
        })
    }

    private fun getIntentData() {
        userMail = intent.getStringExtra("email")
        name = intent.getStringExtra("name")
        photoUrl = intent.getStringExtra("photoUrl")
        logInType = intent.getStringExtra("loginType")
    }

    private fun initStatusBar() = window.run {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.black)
    }

    private fun initViewPager() {

        val newsBundle = Bundle().apply {
            putString("email", userMail)
            putString("name", name)
            putString("photoUrl", photoUrl)
        }

        val weatherBundle = Bundle().apply {
            putString("location", currentLocation)
        }

        val aboutBundle = Bundle().apply {
            putString("loginType", logInType)
            putString("name", name)
            putString("photoUrl", photoUrl)
        }

        adapter.apply {
            val newsFragment = NewsFragment()
            newsFragment.arguments = newsBundle
            addFragment(newsFragment)

            val weatherFragment = WeatherFragment()
            weatherFragment.arguments = weatherBundle
            addFragment(weatherFragment)

            val aboutFragment = AboutFragment()
            aboutFragment.arguments = aboutBundle
            addFragment(aboutFragment)
        }

        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = adapter.count
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
        when (item.itemId) {
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
