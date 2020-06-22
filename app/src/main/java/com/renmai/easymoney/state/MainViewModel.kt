package com.renmai.easymoney.state

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.network.library.listener.ILoadingView
import com.renmai.kdemo.net.ApiService
import com.renmai.kdemo.net.safeLoaddingLaunch
import kotlinx.coroutines.launch


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

    fun getIndexStatus() {
        viewModelScope.safeLoaddingLaunch(iLoadingView) {
            name.set(ApiService.instance.getIndexStatus().data.toString())
        }

    }
}