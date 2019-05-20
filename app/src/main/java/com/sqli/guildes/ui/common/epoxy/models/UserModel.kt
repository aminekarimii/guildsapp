package com.sqli.guildes.ui.common.epoxy.models

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.sqli.guildes.R
import com.sqli.guildes.ui.common.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_user)
abstract class UserModel : EpoxyModelWithHolder<UserModel.UserViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute
    lateinit var fullname: String
    @EpoxyAttribute
    lateinit var site: String

    override fun bind(holder: UserViewHolder) {
        super.bind(holder)
        holder.root.setOnClickListener(clickListener)
        holder.tvUserFullName.text = fullname
        holder.tvUserSite.text = site
    }

    inner class UserViewHolder : KotlinEpoxyHolder() {
        val root by bind<CardView>(R.id.itemUser)
        val tvUserFullName by bind<TextView>(R.id.tvUserFullName)
        val tvUserSite by bind<TextView>(R.id.tvUserSite)
    }
}
