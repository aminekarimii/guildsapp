package com.sqli.guildes.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil.handleError
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import log

class HomeViewModel(dataManager : DataManager) : BaseViewModel(dataManager) {

    private val _guildes = MutableLiveData<Resource<List<Guilde>>>()
    val guildes: LiveData<Resource<List<Guilde>>>
        get() = _guildes

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String> get()  = _message

    fun loadGuildes() {

        _guildes.postValue(Resource.Loading())
        dataManager.getTopGuildes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onSuccess =  _guildes::postValue,
                        onError = {
                            val err = handleError(it, "loadGuildes")
                            _message.postValue(err)
                        }
                )
                .disposeWith(compositeDisposable)
    }
}
