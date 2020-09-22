package com.example.hcassessment.core.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<V : ViewDataBinding>: DaggerFragment() {

    protected val gson = Gson()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var baseView: View

    protected lateinit var binding: V

    val baseActivity: BaseActivity<*> by lazy (mode = LazyThreadSafetyMode.NONE){
        activity as BaseActivity<*>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        baseView = binding.root
        return baseView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(savedInstanceState)
    }

    protected abstract fun onCreated(savedInstance: Bundle?)

    fun goToActivity(currentActivity: Activity, mClass: Class<*>, finishCurrentActivity: Boolean, extras: Bundle? = null,
                     withResult: Boolean = false, requestCode: Int = -1) {
        val intent = Intent(currentActivity, mClass)
        extras?.apply { intent.putExtras(this) }
        if (!withResult)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)

        if (finishCurrentActivity)
            currentActivity.finish()

    }

    protected fun showMessage(message: String, positive: Boolean, neutral: Boolean = false){
        activity?.let {
            (it as BaseActivity<*>).showMessage(message, positive, neutral)
        }
    }
}