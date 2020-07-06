package com.renmai.easymoney.state

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.network.library.listener.ILoadingView
import com.renmai.easymoney.entity.IndexStatus
import com.renmai.kdemo.net.ApiService
import com.renmai.kdemo.net.safeLoaddingLaunch


/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2020-06-17 14:27
 * @depiction   ：
 */

class MainViewModel(
    val application: Application,
    val iLoadingView: ILoadingView
) : ViewModel() {
    val name = ObservableField<String>()

    fun sucess() {
        iLoadingView.showEmpty("成功啦")
    }

    fun getIndexStatus() {

        viewModelScope.safeLoaddingLaunch<IndexStatus>(iLoadingView, IndexStatus::class.java, {
            name.set(ApiService.instance.getIndexStatus<IndexStatus>("http://ryh-app-test.renmaitech.com/api/indexStatus").data.toString())
        }, {

            println("--->callback:success:" + it.toString())
        }, {

            println("--->callback:failcode:" + it)
        })

    }
}