package com.sqli.guildes.ui.common.controllers

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.bumptech.glide.RequestManager
import com.sqli.guildes.core.Resource

import com.sqli.guildes.data.models.Guilde
import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.common.infoText
import com.sqli.guildes.ui.common.loading
import com.sqli.guildes.ui.common.user

class UserController(private val callbacks: Callbacks? = null)
    : TypedEpoxyController<Resource<List<User>>>() {

    interface Callbacks {
        fun onUserItemClicked(id: String, sharedView: View?)
    }

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
                                callbacks?.onUserItemClicked(id, clickedView)
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