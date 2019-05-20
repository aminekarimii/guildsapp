package com.sqli.guildes.ui.common.epoxy.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.sqli.guildes.R
import com.sqli.guildes.ui.common.epoxy.KotlinEpoxyHolder


@EpoxyModelClass(layout = R.layout.view_loading)
abstract class LoadingModel: EpoxyModelWithHolder<LoadingModel.LoadingHolder>() {

    @EpoxyAttribute
    lateinit var description: String

    override fun bind(holder: LoadingHolder) {
        super.bind(holder)
        holder.description.text = description
    }

    override fun unbind(holder: LoadingHolder) {
        super.unbind(holder)
        holder.description.text = ""
    }

    inner class LoadingHolder: KotlinEpoxyHolder() {
        val description by bind<TextView>(R.id.loadingDescriptionTv)
    }
}