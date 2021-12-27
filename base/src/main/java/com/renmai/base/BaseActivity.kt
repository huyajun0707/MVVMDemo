package com.renmai.component.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.netlibrary.cache.adapter.ViewModelFactory
import com.github.xubo.statusbarutils.StatusBarUtils
import com.network.library.listener.ILoadingView
import com.renmai.component.utils.MToast
import com.renmai.component.widget.dialog.ProgressLoadingDialog


/**
 * @author ： HuYajun <huyajun0707>@gmail.com>
 * @version ： 1.0
 * @date ： 2020-06-12 15:18
 * @depiction ：
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity(),
    View.OnClickListener, ILoadingView {

    private lateinit var mBinding: V
    private var dialogFragment: DialogFragment? = null
    private var mActivityProvider: ViewModelProvider? = null

    protected abstract fun getDataBindingConfig(): DataBindingConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBarStatus()
        initViewModel()
        initViewDataBinding()
        initToolbar()
        initExtraIntent()
        initView()
        initListener()
        initData()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initExtraIntent()
    }

    protected fun initExtraIntent() {

    }

    protected abstract fun initViewModel()
    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     *
     *
     * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
     *
     * @return
     */
    protected fun getBinding(): V {
        return this.mBinding
    }


    protected fun initBarStatus() {
        StatusBarUtils.setStatusBarColorLight(this, Color.WHITE)
    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        val dataBindingConfig = getDataBindingConfig()
        val binding = DataBindingUtil.setContentView<V>(this, dataBindingConfig.layout)
        binding.lifecycleOwner = this
        binding.setVariable(dataBindingConfig.variableId, dataBindingConfig.stateViewModel)
        val bindingParams = dataBindingConfig.bindingParams
        bindingParams.forEach {
            binding.setVariable(it.key, it.value)
        }
        mBinding = binding

    }

    protected fun <T : ViewModel> getActivityViewModel(@NonNull modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(
                this,
                ViewModelFactory(application, this)
            )
        }
        return mActivityProvider!!.get(modelClass)
    }


    override fun onDestroy() {
        super.onDestroy()
        destroyViewAndThing()
    }

    /**初始化toolbar*/
    abstract fun initToolbar()

    /**初始化view*/
    abstract fun initView()

    /**初始化事件监听*/
    abstract fun initListener()

    /**初始化数据*/
    abstract fun initData()

    /*执行销毁动作**/
    protected abstract fun destroyViewAndThing()

    override fun showLoading() {
        println("---->showLoading")
        //是否判断在前台
        dialogFragment?.dismiss()
        if (dialogFragment == null) {
            dialogFragment = ProgressLoadingDialog()
        }
        dialogFragment?.show(supportFragmentManager, "progressLoadingDialog")
    }

    override fun hideLoading() {
        println("---->hideLoading")
        dialogFragment?.dismiss()
    }

    fun showToast(msg: String) {
        MToast.showNormal(msg)
    }

    override fun showFailureMessage(msg: String) {
        MToast.showNormal(msg)
    }

    override fun showErrorMessage(msg: String) {
        MToast.showError(msg)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.mImLeftIcon -> finish()
        }
        normalOnClick(v)

    }

    override fun showEmpty(msg: String?) {
        MToast.showNormal(msg!!)
    }


    abstract fun normalOnClick(v: View?)


    /**
     * 系统低内存环境：在系统内存不足，所有后台程序（优先级为background的进程，不是指后台运行的进程）都被杀死时，系统会调用OnLowMemory
     * 参考：https://www.cnblogs.com/sudawei/p/3527145.html
     *      https://www.jianshu.com/p/a5712bdb2dfd
     */
    override fun onLowMemory() {
        super.onLowMemory()
        //清除Glide内存缓存，必须在UI线程中调用
        Glide.get(this).clearMemory()
    }

    /**
     * 应用程序在不同的情况下进行自身的内存释放，以避免被系统直接杀掉，提高应用程序的用户体验
     * 更多OnTrimMemory优化参考：https://www.jianshu.com/p/5b30bae0eb49
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            TRIM_MEMORY_UI_HIDDEN, //内存不足，并且该进程的UI已经不可见了
            TRIM_MEMORY_RUNNING_CRITICAL,//内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
            TRIM_MEMORY_RUNNING_LOW,//内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
            TRIM_MEMORY_RUNNING_MODERATE//内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
            -> {
                Glide.get(this).clearMemory()
            }
            else -> {
            }
        }
        Glide.get(this).trimMemory(level)
    }

    /**
     * 启动新页面
     */
    open fun startActivity(clazz: Class<*>) {
        var intent = Intent(this, clazz)
        startActivity(intent)
    }


}
