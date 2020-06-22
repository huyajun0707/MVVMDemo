package com.renmai.easymoney

import android.view.View
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


    }

    override fun destroyViewAndThing() {

    }

    override fun normalOnClick(v: View?) {

    }


}
