package com.sqli.guildes.ui.login


import androidx.lifecycle.ViewModel
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.remote.responses.LoginResponse
import com.sqli.guildes.dev.LL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import plusAssign

class LoginViewModel (private var mDataManager : DataManager) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    fun startLogin() {
        LL.e("1")
        compositeDisposable += mDataManager
                .login("akherbouch","123456789")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LoginResponse>() {
                    override fun onError(e: Throwable) { e.printStackTrace(); LL.e("err")}

                    override fun onNext(data: LoginResponse) { LL.e(""+data.username) }

                    override fun onComplete() {}
                })
    }



    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}
