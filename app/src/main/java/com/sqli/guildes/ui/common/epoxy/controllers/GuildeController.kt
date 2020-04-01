package com.sqli.guildes.ui.common.epoxy.controllers

import com.airbnb.epoxy.TypedEpoxyController
import com.sqli.guildes.core.Resource
import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.ui.common.epoxy.interfaces.Callbacks
import com.sqli.guildes.ui.common.epoxy.models.guilde
import com.sqli.guildes.ui.common.epoxy.models.infoText
import com.sqli.guildes.ui.common.epoxy.models.loading

class GuildeController(private val callbacks: Callbacks? = null)
    : TypedEpoxyController<Resource<List<Guilde>>>() {

    override fun buildModels(data: Resource<List<Guilde>>?) {
        when (data) {
            is Resource.Success ->
                data.data.forEach {
                    with(it) {
                        guilde {
                            id(id)
                            name(name)
                            points(points)
                            clickListener { _, _, clickedView, _ ->
                                callbacks?.onItemClicked(id, clickedView)
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