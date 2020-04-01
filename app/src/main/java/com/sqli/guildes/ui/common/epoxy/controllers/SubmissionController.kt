package com.sqli.guildes.ui.common.epoxy.controllers

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.ui.common.epoxy.interfaces.Callbacks
import com.sqli.guildes.ui.common.epoxy.models.infoText
import com.sqli.guildes.ui.common.epoxy.models.loading
import com.sqli.guildes.ui.common.epoxy.models.submission
import com.sqli.guildes.utils.DateUtil.toReadableDateAndTime

class SubmissionController(private val callbacks: Callbacks? = null)
    : TypedEpoxyController<Resource<List<Submission>>>() {


    override fun buildModels(data: Resource<List<Submission>>?) {
        when (data) {
            is Resource.Success -> {
                if (!data.data.isNullOrEmpty()) {
                    data.data.forEach {
                        with(it) {
                            submission {
                                id(id)
                                subject(subject)
                                creationDate(toReadableDateAndTime(createdAt))
                                clickListener { _, _, clickedView, _ ->
                                    callbacks?.onItemClicked(id, clickedView)
                                }
                            }
                        }
                    }
                }else{
                    infoText {
                        id("contrib-empty")
                        text("Pas de contributions")
                        spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
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