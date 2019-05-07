package com.sqli.guildes.ui.common

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

import com.sqli.guildes.R

@EpoxyModelClass(layout = R.layout.item_guilde)
abstract class GuildeModel : EpoxyModelWithHolder<GuildeModel.GuildeViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute lateinit var name: String
    @EpoxyAttribute var points : Int = 0

    override fun bind(holder: GuildeViewHolder) {
        super.bind(holder)
        holder.root.setOnClickListener(clickListener)
        holder.tvGuildeName.text = name
        holder.tvGuildePoints.text = points.toString()
    }

    inner class GuildeViewHolder : KotlinEpoxyHolder() {
        val root by bind<CardView>(R.id.itemGuilde)
        val tvGuildeName by bind<TextView>(R.id.tvGuildeName)
        val tvGuildePoints by bind<TextView>(R.id.tvGuildePoints)
    }
}

@EpoxyModelClass(layout = R.layout.item_user)
abstract class UserModel : EpoxyModelWithHolder<UserModel.UserViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute lateinit var fullname: String
    @EpoxyAttribute lateinit var site: String

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

@EpoxyModelClass(layout = R.layout.view_info_text)
abstract class InfoTextModel : EpoxyModelWithHolder<InfoTextModel.InfoTextViewHolder>() {

    @EpoxyAttribute
    lateinit var text: String

    override fun bind(holder: InfoTextViewHolder) {
        super.bind(holder)
        holder.textView.text = text
    }

    inner class InfoTextViewHolder : KotlinEpoxyHolder() {
        val textView by bind<TextView>(R.id.infoTextTv)
    }
}

@EpoxyModelClass(layout = R.layout.view_loading)
abstract class LoadingModel: EpoxyModelWithHolder<LoadingModel.LoadingHolder>() {

    @EpoxyAttribute lateinit var description: String

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