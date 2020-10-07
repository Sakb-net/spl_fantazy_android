package com.sakb.spl.ui.splash


import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sakb.spl.R
import com.sakb.spl.base.BaseActivity
import com.sakb.spl.databinding.ActivitySplashBinding
import com.sakb.spl.ui.login.LoginActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var compositeDisposable: CompositeDisposable? = null
    override val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        compositeDisposable = CompositeDisposable()

        Observable.timer(3, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onNext(t: Long) {

                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable?.add(d)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
        compositeDisposable?.dispose()
    }


}
