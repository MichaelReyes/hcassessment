package com.example.hcassessment.core.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<V : ViewDataBinding> : DaggerAppCompatActivity() {

    protected val gson = Gson()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var binding: V


    val loadingDialog: LoadingDialog by lazy(mode = LazyThreadSafetyMode.NONE) {
        LoadingDialog(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutRes)

        onCreated(savedInstanceState)
    }

    protected abstract fun onCreated(instance: Bundle?)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun showLoading(isLoading: Boolean) {
        loadingDialog.let {
            if (isLoading && !loadingDialog.isShowing)
                loadingDialog.show()
            else if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }
    }

    fun showMessage(message: String, positive: Boolean, neutral: Boolean = false) {
        var snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)

        val sbView = snackbar.view
        when {
            !positive -> {
                sbView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            }
            neutral -> sbView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark))
            else -> sbView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        }

        val textValue = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)

        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }

    fun goToActivity(currentActivity: Activity, mClass: Class<*>, finishCurrentActivity: Boolean, extras: Bundle? = null,
                     withResult: Boolean = false, requestCode: Int = -1) {
        val intent = Intent(currentActivity, mClass)
        extras?.apply { intent.putExtras(this) }
        if (!withResult)
            currentActivity.startActivity(intent)
        else
            currentActivity.startActivityForResult(intent, requestCode)

        if (finishCurrentActivity)
            currentActivity.finish()

    }

    fun setToolbar(show: Boolean = false, showBackButton: Boolean = false, title: String = "") {
        val actionBar = supportActionBar

        actionBar?.run {
            if (show) {
                show()
                displayOptions = ActionBar.DISPLAY_SHOW_TITLE

                setHomeButtonEnabled(showBackButton)
                setDisplayHomeAsUpEnabled(showBackButton)

                if(title != "") {
                    setDisplayShowTitleEnabled(true)
                    this@run.title = title
                }else
                    setDisplayShowTitleEnabled(false)

            } else
                hide()
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }
}