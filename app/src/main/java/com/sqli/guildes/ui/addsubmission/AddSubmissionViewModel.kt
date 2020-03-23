package com.sqli.guildes.ui.addsubmission

import android.content.Context
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sqli.guildes.R
import com.sqli.guildes.core.Constants.SUBMISSIONS_TYPES
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.disposeWith
import com.sqli.guildes.data.DataManager
import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.base.BaseViewModel
import com.sqli.guildes.utils.ErrorUtil
import com.sqli.guildes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import log
import java.util.*

class AddSubmissionViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _submission = SingleLiveEvent<Boolean>()
    val submission: LiveData<Boolean> get() = _submission

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    val currentUser: User?
        get() = dataManager.currentUserPref

    private val _spinnerOptions = MutableLiveData<ArrayAdapter<CharSequence>>()
    val spinnerOptions: LiveData<ArrayAdapter<CharSequence>> get() = _spinnerOptions

    fun addSubmission(subject: String, description: String, type: String, points: Int) {
        if (!isInputsValid(subject, description, type, points))
            _message.postValue("Please complete the inputs")
        dataManager.postSubmission(subject, type, description, points)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            when (it) {
                                is Resource.Success -> _submission.postValue(true)
                                is Resource.Error -> _message.postValue(it.errorMessage)
                            }
                        },
                        onError = {
                            val err = ErrorUtil.handleError(it, "postNewSubmission")
                            _message.postValue(err)
                        }
                ).disposeWith(compositeDisposable)
    }

    private fun isInputsValid(subject: String, description: String, type: String, points: Int)
            : Boolean = !TextUtils.isEmpty(subject)
            && !TextUtils.isEmpty(description)
            && !TextUtils.isEmpty(type)
            && !TextUtils.isEmpty(points.toString())

    fun createSpinnerAdapter(context: Context) {
        _spinnerOptions.postValue(
                ArrayAdapter.createFromResource(context, R.array.submissions_types,
                        android.R.layout.simple_spinner_item)
        )
    }

    fun getSubmissionPoints(position: Int): Int {
        return SUBMISSIONS_TYPES.toList()[position].second
    }

    fun getSubmissionType(position: Int): String {
        return SUBMISSIONS_TYPES.toList()[position].first
    }

    fun getDrawableRes(context: Context): Int {
        val guildName = currentUser!!.guilde.name.replace("é", "e")
                .replace("è", "e")
                .toLowerCase(Locale.getDefault())
        log(guildName)
        return context.resources.getIdentifier(guildName,
                "drawable", context.packageName)
    }
}