package com.renmai.component.base

import androidx.lifecycle.ViewModel


/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2020-06-12 16:20
 * @depiction   ：
 */

/**
 * TODO tip:
 * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 * 通过这样的方式，来彻底解决 视图调用的一致性问题，
 * 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。
 * 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
 *
 * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
 *
 * Create by KunMinX at 20/4/18
 */
class DataBindingConfig(var layout: Int, val stateViewModel: ViewModel, val variableId: Int) {

    val bindingParams = mutableMapOf<Int,Any>()

    fun addBindingParam(variableId: Int, any: Any): DataBindingConfig {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, any)
        }
        return this
    }
}