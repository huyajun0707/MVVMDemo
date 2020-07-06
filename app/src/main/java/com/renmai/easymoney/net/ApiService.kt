package com.renmai.kdemo.net

import com.renmai.base.BaseApplication
import com.renmai.utils.UserUtils
import com.renmai.easymoney.BuildConfig
import com.renmai.easymoney.entity.IndexStatus
import com.network.library.NetworkEngine
import com.renmai.component.network.BaseEntity
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2019-08-06 23:03
 */
interface ApiService {

    companion object {

        val instance: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkEngine.builder().setApplication(BaseApplication.instance())
                .setHeaderMap(mutableMapOf(Pair("Authorization", { UserUtils.getToken() })))
                .setDebug(BuildConfig.DEBUG)
                .baseUrl("http://ryh-app-test.renmaitech.com/")
                .build()
                .create(ApiService::class.java)
        }

    }

    @GET
    suspend fun <T> getIndexStatus(@Url url: String): BaseEntity<T>

}