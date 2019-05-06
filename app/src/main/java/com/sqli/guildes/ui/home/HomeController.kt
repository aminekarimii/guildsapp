package com.sqli.guildes.ui.home

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.sqli.guildes.core.Resource

import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.ui.common.guilde
import com.sqli.guildes.ui.common.infoText
import com.sqli.guildes.ui.common.loading

class HomeController(private val callbacks: Callbacks,
                     private val glide: RequestManager)
    : TypedEpoxyController<Resource<List<Guilde>>>() {

    interface Callbacks {
        fun onGuildeItemClicked(id: String, transitionName: String = "", sharedView: View?)
    }

    override fun buildModels(data: Resource<List<Guilde>>?) {
        when (data) {
            is Resource.Success -> data.data.forEach {
                with(it) {
                    guilde {
                        id(id)
                        name(name)
                        points(points)
                        glide(glide)
                        transitionName("logo-$id")
                        clickListener { model, _, clickedView, _ ->
                            callbacks.onGuildeItemClicked(id, model.transitionName, clickedView)
                        }
                    }
                }
            }
            is Resource.Error -> {
                infoText {
                    id("error-top-guildes")
                    text("Error getting top guildes")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Loading -> {
                loading {
                    id("load-top-guildes")
                    description("Loading ...")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
        }
    }

}