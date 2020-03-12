package com.sqli.guildes.ui.addsubmission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AddSubmissionViewModel(dataManager: DataManager): BaseViewModel(dataManager) {

    private val _submission = MutableLiveData<Resource<String>>()
    val submission : LiveData<Resource<String>> get() = _submission

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> get() = _message

    fun addSubmission(subject: String){
        _submission.postValue(Resource.Loading())
        dataManager.postSubmission(subject)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            _submission.postValue(it)
                        },
                        onError = {
                            val err = ErrorUtil.handleError(it, "postNewSubmission")
                            _message.postValue(err)
                        }
                ).disposeWith(compositeDisposable)
    }

}