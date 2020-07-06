package com.renmai.kdemo.net

import android.os.Handler
import android.os.Looper
import com.google.gson.JsonParseException
import com.network.library.listener.ILoadingView
import com.renmai.component.network.BaseEntity
import com.renmai.component.utils.GsonUtils
import com.renmai.component.utils.ReflectionUtils
import kotlinx.coroutines.*
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2020-01-07 18:49
 * @depiction   ： 网络请求统一处理
 */


val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

}

private val handler = Handler(Looper.getMainLooper())

//用拦截器实现code处理 并切换线程
class NetWorkContinuationInterceptor<T>(
    val view: ILoadingView?,
    val clazz: Class<T>,
    val successBlock: (t: T) -> Unit,
    val failBlock: (code: String) -> Unit
) : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <D> interceptContinuation(continuation: Continuation<D>) =
        NetWorkContinuation<D, T>(continuation, view, clazz, successBlock, failBlock)
}

class NetWorkContinuation<T, D>(
    val continuation: Continuation<T>,
    val view: ILoadingView?,
    val clazz: Class<D>,
    val successBlock: (t: D) -> Unit,
    val failBlock: (code: String) -> Unit
) :
    Continuation<T> {
    override val context = continuation.context

    override fun resumeWith(result: Result<T>) {
        result.onSuccess { it ->
            if (it != null) {
                try {
                    var baseResponse = it as BaseEntity<D>
                    if (baseResponse != null) {
                        println("--->code:${baseResponse.data.toString()}")
                        if (baseResponse.code.equals("200")) {
                            //展示数据
                            println("--->clazz:" + NetWorkContinuation@ this.javaClass.name)
                            handler.post(Runnable {
                                continuation.resumeWith(result)
//                                var result = GsonUtils.getModel(it.toString(), clazz)
                                successBlock.invoke(it.data as D)
                            })
                            return@resumeWith
                        } else {
                            handler.post(Runnable {
                                view?.showFailureMessage(baseResponse.msg!!)
                                failBlock.invoke(baseResponse.code!!)
                            })
                            return@resumeWith
                        }
                    } else {
                        //BaseResponse为空，显示获取数据失败视图
                        handler.post(Runnable {
                            failBlock.invoke("99999")
                            view?.showFailureMessage("未获取到网络数据")
                        })
                        return@resumeWith
                    }
                } catch (e: java.lang.Exception) {

                }
            }

        }
        continuation.resumeWith(result)
    }
}


fun <T> CoroutineScope.safeLoaddingLaunch(
    view: ILoadingView?,
    clazz: Class<T>,
    block: suspend () -> Unit,
    successBlock: (t: T) -> Unit,
    failBlock: (code: String) -> Unit
): Job = launch(NetWorkContinuationInterceptor(view, clazz, successBlock, failBlock)) {
    try {
        view?.showLoading()
        block()
    } catch (e: Exception) {
        handler.post(Runnable {
            handlerException(e, view)
            failBlock.invoke("99999")
        })
    } finally {
        handler.post(Runnable {
            view?.hideLoading()
        })
    }
}


fun <T> CoroutineScope.safeLoaddingLaunch(
    view: ILoadingView?,
    clazz: Class<T>,
    block: suspend () -> Unit,
    successBlock: (t: T) -> Unit
): Job = launch(NetWorkContinuationInterceptor(view,clazz, successBlock, {})) {
    try {
        view?.showLoading()
        block()
    } catch (e: Exception) {
        handler.post(Runnable {
            handlerException(e, view)
        })
    } finally {
        handler.post(Runnable {
            view?.hideLoading()
        })
    }
}


fun <T> CoroutineScope.safeLoaddingLaunch(
    view: ILoadingView?,
    clazz: Class<T>,
    block: suspend () -> Unit
): Job = launch(NetWorkContinuationInterceptor(view, clazz,{}, {})) {
    try {
        view?.showLoading()
        block()
    } catch (e: Exception) {
        handler.post(Runnable {
            handlerException(e, view)
        })
    } finally {
        handler.post(Runnable {
            view?.hideLoading()
        })
    }
}


fun <T> CoroutineScope.safeNoLoaddingLaunch(
    view: ILoadingView,
    clazz: Class<T>,
    block: suspend () -> Unit
): Job = launch(NetWorkContinuationInterceptor<T>(view,clazz, {}, {})) {
    try {
        block()
    } catch (e: Exception) {
        handler.post(Runnable {
            handlerException(e, view)
        })
    } finally {
    }

}


/**
 * 异常统一处理函数
 */
fun handlerException(e: Exception, view: ILoadingView?) {
    println("-->${e.message}")
    if (e is HttpException) {     //   HTTP错误
        onCallException(view, com.network.library.observer.ExceptionReason.BAD_NETWORK)
    } else if (e is ConnectException
        || e is UnknownHostException
    ) {   //   连接错误
        onCallException(view, com.network.library.observer.ExceptionReason.CONNECT_ERROR)
    } else if (e is InterruptedIOException) {   //  连接超时
        onCallException(view, com.network.library.observer.ExceptionReason.CONNECT_TIMEOUT)
    } else if (e is JsonParseException
        || e is JSONException
        || e is ParseException
    ) {   //  解析错误
        onCallException(view, com.network.library.observer.ExceptionReason.PARSE_ERROR)
    } else {
        onCallException(view, com.network.library.observer.ExceptionReason.UNKNOWN_ERROR)
    }
}

/**
 * 异常统一处理函数
 */
fun onCallException(
    veiw: ILoadingView?,
    exception: com.network.library.observer.ExceptionReason
) {
    when (exception) {
        com.network.library.observer.ExceptionReason.CONNECT_ERROR -> veiw?.showErrorMessage(
            "网络连接异常"
        )
        com.network.library.observer.ExceptionReason.CONNECT_TIMEOUT -> veiw?.showErrorMessage(
            "网络连接超时"
        )
        com.network.library.observer.ExceptionReason.BAD_NETWORK -> veiw?.showErrorMessage("网络异常")
        com.network.library.observer.ExceptionReason.PARSE_ERROR -> veiw?.showErrorMessage("数据解析失败")
        com.network.library.observer.ExceptionReason.UNKNOWN_ERROR -> veiw?.showErrorMessage(
            "未知错误"
        )
    }
}


