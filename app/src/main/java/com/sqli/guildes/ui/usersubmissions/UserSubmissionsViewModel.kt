package com.sqli.guildes.ui.usersubmissions

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

class UserSubmissionsViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _contributions = MutableLiveData<Resource<List<Submission>>>()
    val contributions: LiveData<Resource<List<Submission>>>
        get() = _contributions

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>>
        get() = _user

    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String>
        get() = _message

    fun getUserContributions(userId:String) {
        _contributions.postValue(Resource.Loading())
        dataManager.getUserContributions(userId)
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = _contributions::postValue,
                        onError = {
                            it.printStackTrace()
                            val err = ErrorUtil.handleError(it, "loadUserContributions")
                            _message.postValue(err)
                        }
                ).disposeWith(compositeDisposable)
    }

    fun getUserById(userId: String){
        _user.postValue(Resource.Loading())
        dataManager.getUserById(userId)
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = _user::postValue,
                        onError = {
                            it.printStackTrace()
                            val err = ErrorUtil.handleError(it, "loadUserError")
                            _message.postValue(err)
                        }
                ).disposeWith(compositeDisposable)
    }
}