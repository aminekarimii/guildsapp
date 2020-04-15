package com.sqli.guildes.ui.validation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ValidationViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private var _contribution = MutableLiveData<Resource<Submission>>()
    val contribution: LiveData<Resource<Submission>> get() = _contribution

    fun getContribution(submissionId: String) {
        _contribution.postValue(Resource.Loading())
        dataManager.getSubmission(submissionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = _contribution::postValue
                ).disposeWith(compositeDisposable)
    }

}