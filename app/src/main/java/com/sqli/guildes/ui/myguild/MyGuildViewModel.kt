package com.sqli.guildes.ui.myguild

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MyGuildViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val currentUser: User?
        get() = dataManager.currentUserPref

    private var _guildContributions = MutableLiveData<Resource<List<Submission>>>()
    val guildContributions: LiveData<Resource<List<Submission>>>
        get() = _guildContributions

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String> get() = _message

    fun loadGuildContributions() {
        _guildContributions.postValue(Resource.Loading())
        dataManager.getGuildSubmissions(currentUser?.guilde!!.id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = _guildContributions::postValue,
                        onError = {
                            val err = ErrorUtil.handleError(it, "loadGuildContributions")
                            _message.postValue(err)
                        }

                ).disposeWith(compositeDisposable)
    }

}