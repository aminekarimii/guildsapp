package com.sqli.guildes.ui.common.epoxy.controllers

import android.content.Context
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.ui.common.epoxy.models.infoText
import com.sqli.guildes.ui.common.epoxy.models.loading
import com.sqli.guildes.ui.common.epoxy.models.submission
import com.sqli.guildes.utils.DateUtil.toReadableDateAndTime

class SubmissionController(var context: Context, private val callbacks: Callbacks? = null)
    : TypedEpoxyController<Resource<List<Submission>>>() {

    interface Callbacks {
        fun onSubmissionClicked(id: String, sharedView: View?)
    }

    override fun buildModels(data: Resource<List<Submission>>?) {
        when (data) {
            is Resource.Success -> {
                data.data.forEach {
                    with(it) {
                        submission {
                            id(id)
                            subject(subject)
                            creationDate(toReadableDateAndTime(context, createdAt))
                            clickListener { _, _, clickedView, _ ->
                                callbacks?.onSubmissionClicked(id, clickedView)
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                infoText {
                    id("error-submissions")
                    text(context.getString(R.string.user_error_msg))
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Loading -> {
                loading {
                    id("load-submissions")
                    description(context.getString(R.string.loading_submissions_msg))
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
        }
    }
}