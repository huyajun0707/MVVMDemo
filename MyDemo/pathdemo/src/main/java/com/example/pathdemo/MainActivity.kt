package com.example.kdemo

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.kdemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import android.database.Observable as Observable1
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.dakai.android.network.NetworkEngine
import com.dakai.android.network.domain.BaseResponse
import com.example.kdemo.adapter.HomeAdapter
import com.example.pathdemo.NameViewModel
import com.example.pathdemo.PathUtil
import com.example.pathdemo.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import android.widget.LinearLayout.HORIZONTAL as HORIZONTAL1


class MainActivity : AppCompatActivity() {

    var view: View ? =null
//    lateinit var myViewModel:MyViewModel
    lateinit var nameViewModel: NameViewModel

    public var str:String = "测试"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
//        lifecycle.addObserver(LifecycleObserverDemo())
//        myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
////        tvGet.setText(myViewModel.number.toString())
//
//        button1.setOnClickListener(View.OnClickListener {
//            myViewModel.number++
//            tvGet.setText(myViewModel.number.toString())
//        })
        var i:Int = 0
        button2.setOnClickListener(View.OnClickListener {
//            nameViewModel.currentName.value = "s"+i++
//            Klog.Companion.getSettings().setBorderEnable(true)
//            Klog.i("bbbbbbb")
//            "hahahahaha".log()
//            var js:JSONObject = JSONObject()
//            js.put("a","aaaa")
//            js.put("b","bbbb")
//            js.put("c","cccc")
//           Klog.json(js)
//            context.getResources().getResourceEntryName(view.getId()
            //获取view上的名字
//           var name =getResources().getResourceEntryName(binding.button2.id)
//            var name = resources.getResourcePackageName(binding.button2.id)
//            var name = binding.button1.context::class.java.name
//            println("--->$name")
//            button2.setText(str)
//            var path = PathUtil.getViewPath(button2)
//            var resutlt:String = PathUtil.getDataObj(button2,"this.context.str").toString()
//
//            println("--->path:${path}String:$resutlt")

        })
//
//        getText(view)
//        tvGet.setOnClickListener { view -> XLog.d("--------log") }
//// Specify the current activity as the lifecycle owner.

        nameViewModel=ViewModelProviders.of(this,SavedStateVMFactory(this)).get(NameViewModel::class.java)

        binding.data = nameViewModel;
        binding.setLifecycleOwner(this)

        // Create the observer which updates the UI.
        val nameObserver = object : Observer<Int> {
            override fun onChanged(@Nullable newName: Int) {
                // Update the UI, in this case, a TextView.
//                tvGet.setText(newName)
            }
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        nameViewModel.number.observe(this, nameObserver)

        var drawable: Drawable? = getDrawable(R.mipmap.icon_close)
//        binding
       var data = arrayListOf<String>("1","2","3","4","5","6","7","8","9")

        var  adapter = HomeAdapter(this,data)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter =adapter
        adapter.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->


            var path = PathUtil.getViewPath(view)

            println("--->path:$path")

        })


        NetworkEngine.instance.getApi().testNetwork().observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object :io.reactivex.Observer<BaseResponse<String>>{
                override fun onNext(t: BaseResponse<String>) {
                    println("--->$t")
                }

                override fun onComplete() {
                    println("--->onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("--->onSubscribe")
                }

                override fun onError(e: Throwable) {
                    println("--->onError${e.message}")
                }

            })

    }
}

fun getText(view: View?){
    print(view?.id)
}
