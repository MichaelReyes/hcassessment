package com.example.hcassessment.feature.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hcassessment.R
import com.example.hcassessment.core.base.BaseActivity
import com.example.hcassessment.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {
    override val layoutRes = R.layout.activity_dashboard

    override fun onCreated(instance: Bundle?) {
        setSupportActionBar(toolbar)
    }
}