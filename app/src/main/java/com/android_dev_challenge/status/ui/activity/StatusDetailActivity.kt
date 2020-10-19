package com.android_dev_challenge.status.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android_dev_challenge.status.R
import com.android_dev_challenge.status.common.AppConstants.STATUS_DETAIL
import com.android_dev_challenge.status.databinding.ActivityStatusDetailBinding
import com.android_dev_challenge.status.ui.dto.StatusDto

class StatusDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val status = intent.getParcelableExtra<StatusDto.ChildStatus>(STATUS_DETAIL)

        val binding: ActivityStatusDetailBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_status_detail
        )
        binding.childStatus = status
    }
}