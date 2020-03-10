package com.sqli.guildes.ui.common.epoxy.controllers

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.ui.common.epoxy.models.infoText
import com.sqli.guildes.ui.common.epoxy.models.loading
import com.sqli.guildes.ui.common.epoxy.models.submission

class SubmissionController(private val callbacks: Callbacks? = null)
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
                            site(createdBy.site)
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
                    text("Error getting users")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Loading -> {
                loading {
                    id("load-submissions")
                    description("Loading Your Submissions ...")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
        }
    }
}