package com.sqli.guildes.ui.profile

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
import log

class ProfileViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    // current contributor
    private val _currentContributor = MutableLiveData<Resource<User>>()
    val currentContributor: LiveData<Resource<User>>
        get() = _currentContributor

    private val _submissions = MutableLiveData<Resource<List<Submission>>>()
    val submissions: LiveData<Resource<List<Submission>>>
        get() = _submissions

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String> get() = _message

    fun loadCurrentUserDetails() : ProfileViewModel{
        _currentContributor.postValue(Resource.Loading())
        dataManager.getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            _currentContributor.postValue(it)
                        },
                        onError = {
                            val err = ErrorUtil.handleError(it, "loadCurrentUser")
                            _message.postValue(err)
                        }
                )
                .disposeWith(compositeDisposable)
        return this
    }

    fun loadCurrentUserSubmissions() {
        _submissions.postValue(Resource.Loading())
        dataManager.getSubmissionsCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = _submissions::postValue,
                        onError = {
                            val err = ErrorUtil.handleError(it, "loadCurrentUserContributions")
                            _message.postValue(err)
                        }

                ).disposeWith(compositeDisposable)
    }
}