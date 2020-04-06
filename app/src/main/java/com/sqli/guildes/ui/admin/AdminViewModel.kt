package com.sqli.guildes.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AdminViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _submissions = MutableLiveData<Resource<List<Submission>>>()
    val submissions: LiveData<Resource<List<Submission>>>
        get() = _submissions

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String> get() = _message

    fun loadAllSubmissions() {
        _submissions.postValue(Resource.Loading())
        dataManager.getAllSubmissions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = _submissions::postValue,
                        onError = {
                            val err = ErrorUtil.handleError(it, "loadAllSubmissions")
                            _message.postValue(err)
                        }
                ).disposeWith(compositeDisposable)
    }
}
