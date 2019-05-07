package com.sqli.guildes.ui.common.controllers

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.sqli.guildes.core.Resource

import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.ui.common.guilde
import com.sqli.guildes.ui.common.infoText
import com.sqli.guildes.ui.common.loading

class GuildeController(private val callbacks: Callbacks? = null)
    : TypedEpoxyController<Resource<List<Guilde>>>() {

    interface Callbacks {
        fun onGuildeItemClicked(id: String, sharedView: View?)
    }

    override fun buildModels(data: Resource<List<Guilde>>?) {
        when (data) {
            is Resource.Success -> data.data.forEach {
                with(it) {
                    guilde {
                        id(id)
                        name(name)
                        points(points)
                        clickListener { _, _, clickedView, _ ->
                            callbacks?.onGuildeItemClicked(id, clickedView)
                        }
                    }
                }
            }
            is Resource.Error -> {
                infoText {
                    id("error-guildes")
                    text("Error getting guildes")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Loading -> {
                loading {
                    id("load-guildes")
                    description("Loading Guildes ...")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
        }
    }

}