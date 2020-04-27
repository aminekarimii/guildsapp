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
import com.sqli.guildes.utils.SingleLiveEvent
import com.sqli.guildes.utils.StringUtil.formatName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddSubmissionViewModel(dataManager: DataManager) : BaseViewModel(dataManager) {

    private val _submission = SingleLiveEvent<Boolean>()
    val submission: LiveData<Boolean> get() = _submission

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> get() = _message

    val currentUser: User?
        get() = dataManager.currentUserPref

    private val _spinnerOptions = MutableLiveData<ArrayAdapter<CharSequence>>()
    val spinnerOptions: LiveData<ArrayAdapter<CharSequence>> get() = _spinnerOptions

    fun addSubmission(subject: String, description: String, type: String, points: Int) {
        if (!areInputsValid(subject, description, type, points))
            return _message.postValue(R.string.guild_empty_field)

        dataManager.postSubmission(subject, type, description, points)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            when (it) {
                                is Resource.Success -> _submission.postValue(true)
                                is Resource.Error -> _message.postValue(R.string.guild_network_error)
                            }
                        },
                        onError = {
                            _message.postValue(R.string.guild_error)
                        }
                ).disposeWith(compositeDisposable)
    }

    private fun areInputsValid(subject: String, description: String, type: String, points: Int)
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
        return if (position <= SUBMISSIONS_TYPES.size)
            SUBMISSIONS_TYPES.toList()[position].second
        else -1
    }

    fun getSubmissionType(context: Context, position: Int): String {
        return if (position <= SUBMISSIONS_TYPES.size)
            context.getString(SUBMISSIONS_TYPES.toList()[position].first)
        else "empty"
    }

    fun getDrawableRes(context: Context): Int {
        return context.resources.getIdentifier(
                currentUser!!.guilde.name.formatName().toLowerCase(Locale.ROOT),
                "drawable", context.packageName
        )
    }
}