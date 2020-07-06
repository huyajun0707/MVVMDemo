package com.renmai.component.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import org.jetbrains.annotations.NotNull
import androidx.appcompat.app.AppCompatActivity
import com.network.library.listener.ILoadingView
import com.renmai.component.utils.MToast


/**
 * @author      ： HuYajun <huyajun0707@gmail.com>
 * @version     ： 1.0
 * @date        ： 2020-06-12 17:41
 * @depiction   ：
 */

abstract class BaseFragment<V : ViewDataBinding> : Fragment(),
    View.OnClickListener, ILoadingView {


    protected var mActivity: AppCompatActivity? = null
    protected lateinit var mBinding: V

    /**当前Fragment对用户是否可见*/
    protected var isVisibleToUser: Boolean = false
    /**当前Fragment视图是否实例化*/
    private var isViewInitialized: Boolean = false
    /**当前Fragment数据是否实例化*/
    private var isDataInitialized: Boolean = false
    /**是否使用懒加载*/
    private var isLazyLoadEnabled = true
    /**是否可以在基类中调用初始化数据（initData()）的方法 */
    private var isInitDataInBase = true

    /**关闭懒加载，在[onAttach]函数调用*/
    protected fun closeLazyLoad() {
        isLazyLoadEnabled = false
    }

    protected var mAnimationLoaded: Boolean = false
    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null


    /**设置不能在基类中初始化数据，开发者自己决定数据何时初始化，在[onAttach]函数调用*/
    protected fun unableInitDataInBase() {
        isInitDataInBase = false
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化presenter

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        initViewDataBinding(inflater, container)
        return mBinding.getRoot()

    }

    /**
     * 注入绑定
     */
    private fun initViewDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        val dataBindingConfig = getDataBindingConfig()

        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。

        // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

        val binding =
            DataBindingUtil.inflate<V>(inflater, dataBindingConfig.layout, container, false)
        binding.setLifecycleOwner(this)
        binding.setVariable(dataBindingConfig.variableId, dataBindingConfig.stateViewModel)
        val bindingParams = dataBindingConfig.bindingParams
        bindingParams.forEach {
            binding.setVariable(it.key, it.value)
        }
        mBinding = binding

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //1、加载视图
        isViewInitialized = true
        initView()
        addLifecycleObserver()
        initListener()
        initViewModel()
        if (isInitDataInBase) {
            //2、可以在基类中加载数据，加载数据
            if (!isLazyLoadEnabled) {
                //2.1需要懒加载
                if (savedInstanceState != null) {
                    onRestoreInstanceState(savedInstanceState)
                }
                if (isDataInitialized) {
                    initData()
                } else {
                    checkHaveLoaded()
                }
            } else {
                //2.2不需要懒加载，直接加载数据
                isDataInitialized = true
                initData()
            }
        }
    }

    protected abstract fun initViewModel()

    protected abstract fun getDataBindingConfig(): DataBindingConfig

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     *
     *
     * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
     *
     * @return
     */
    protected fun getBinding(): ViewDataBinding {

        return mBinding
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        isDataInitialized = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        checkHaveLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewInitialized = false
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyViewAndThing()
    }


    /**检查是否已加载数据*/
    private fun checkHaveLoaded() {
        //当对用户可见，并且视图已经实例化，而数据未实例化的时候，实例化数据
        if (isVisibleToUser && isViewInitialized && !isDataInitialized) {
            isDataInitialized = true
            initData()
        }
    }


    abstract fun initView()

    /**初始化事件监听*/
    abstract fun initListener()

    /**初始化数据*/
    abstract fun initData()


    override fun onClick(v: View?) {
        normalOnClick(v)
    }


    /**
     * 不用检查网络，可以直接触发的点击事件
     *
     * @param v
     */
    open fun normalOnClick(v: View?) {

    }

    protected fun <T : ViewModel> getFragmentViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    protected fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = mActivity?.let { ViewModelProvider(it) }
        }
        return mActivityProvider?.get(modelClass)!!
    }


    /*执行销毁动作**/
    protected abstract fun destroyViewAndThing()


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showEmpty(msg: String?) {
        MToast.showNormal(msg!!)
    }

    override fun showFailureMessage(msg: String) {
        MToast.showError(msg)
    }

    override fun showErrorMessage(msg: String) {
        MToast.showError(msg)
    }

    override fun finishActivity() {
        activity?.finish()
    }


    fun addLifecycleObserver() {
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume(@NotNull owner: LifecycleOwner) {
                println("--->LifecycleObserver:${javaClass.name}：onResume")
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause(@NotNull owner: LifecycleOwner) {
                println("--->LifecycleObserver:${javaClass.name}：onPause")
            }


        })


    }


}