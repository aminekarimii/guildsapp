package com.sqli.guildes.ui.common.epoxy.controllers

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.sqli.guildes.core.Resource

import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.common.epoxy.interfaces.Callbacks
import com.sqli.guildes.ui.common.epoxy.models.infoText
import com.sqli.guildes.ui.common.epoxy.models.loading
import com.sqli.guildes.ui.common.epoxy.models.user

class UserController(private val callbacks: Callbacks? = null)
    : TypedEpoxyController<Resource<List<User>>>() {

    override fun buildModels(data: Resource<List<User>>?) {
        when(data) {
            is  Resource.Success -> {
                data.data.forEach {
                    with(it) {
                        user {
                            id(id)
                            fullname("$firstname $lastname")
                            site(site)
                            clickListener { _, _, clickedView, _ ->
                                callbacks?.onItemClicked(id, clickedView)
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                infoText {
                    id("error-users")
                    text("Error getting users")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
            is Resource.Loading -> {
                loading {
                    id("load-users")
                    description("Loading Users ...")
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
                }
            }
        }
    }



}