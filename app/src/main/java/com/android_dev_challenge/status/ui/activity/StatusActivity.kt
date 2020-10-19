package com.android_dev_challenge.status.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android_dev_challenge.status.R
import com.android_dev_challenge.status.ui.adapter.ParentStatusTabAdapter
import com.android_dev_challenge.status.viewmodel.StatusActivityViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.viewModel

class StatusActivity : AppCompatActivity() {

    val statusActivityViewModel: StatusActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        val tabAdapter = ParentStatusTabAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}