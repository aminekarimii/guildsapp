package com.sqli.guildes.ui.guildedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class GuildeDetailsViewModel(dataManager : DataManager) : BaseViewModel(dataManager) {

    private val _guilde = MutableLiveData<Resource<Guilde>>()
    val guilde: LiveData<Resource<Guilde>>
        get() = _guilde

    private val _guildeContributors = MutableLiveData<Resource<List<User>>>()
    val guildeContributors : LiveData<Resource<List<User>>>
        get() = _guildeContributors


    private val _message = SingleLiveEvent<String>()
    val message: LiveData<String>
        get() = _message


    fun loadGuildeDetails(guildeId : String) {

        _guilde.postValue(Resource.Loading())
        dataManager.getGuildeDetails(guildeId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onSuccess = _guilde::postValue,
                        onError = {
                            val err = ErrorUtil.handleError(it, "loadGuildeDetails")
                            _message.postValue(err)
                        }
                )
                .disposeWith(compositeDisposable)

    }

    fun loadGuildeContributors(guildeId : String) {

        _guildeContributors.postValue(Resource.Loading())
        dataManager.getGuildeConstributors(guildeId)
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onSuccess =  _guildeContributors::postValue,
                        onError = {
                            it.printStackTrace()
                            val err = ErrorUtil.handleError(it, "loadGuildeDetails")
                            _message.postValue(err)
                        }
                )
                .disposeWith(compositeDisposable)

    }

}
