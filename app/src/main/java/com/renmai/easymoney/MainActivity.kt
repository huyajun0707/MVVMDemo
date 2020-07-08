package com.renmai.easymoney

import android.view.View
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.renmai.component.base.BaseActivity
import com.renmai.component.base.DataBindingConfig
import com.renmai.easymoney.databinding.ActivityMainBinding
import com.renmai.easymoney.state.MainViewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var mMainActivityViewModel: MainViewModel? = null

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main, mMainActivityViewModel!!, BR.vm)
    }

    override fun initViewModel() {
        mMainActivityViewModel = getActivityViewModel(MainViewModel::class.java)
    }

    override fun initToolbar() {

    }

    override fun initView() {

    }

    override fun initListener() {

    }

    override fun initData() {
        val email = "123@163.com"
        println("------>"+email.substring(0, email.indexOf("@")))
        println("------>"+email.substring(email.indexOf("@"), email.length))

    }

    override fun destroyViewAndThing() {

    }

    override fun normalOnClick(v: View?) {
       var jsonObject: JsonObject
        var jsonArray:JsonArray

        var  recyclerView:RecyclerView
        recyclerView.doOnLayout {  }
        recyclerView.notifySubtreeAccessibilityStateChanged()

    }


}
