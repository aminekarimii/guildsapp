package com.sqli.guildes.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.RequestManager
import com.sqli.guildes.R

@EpoxyModelClass(layout = R.layout.item_guilde)
abstract class GuildeModel : EpoxyModelWithHolder<GuildeModel.GuildeViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute lateinit var transitionName: String
    @EpoxyAttribute lateinit var glide: RequestManager
    @EpoxyAttribute lateinit var name: String
    @EpoxyAttribute var points : Int = 0

    override fun bind(holder: GuildeViewHolder) {
        super.bind(holder)
        ViewCompat.setTransitionName(holder.guildeLogoImg, transitionName)
        holder.guildeLogoImg.setOnClickListener(clickListener)
        holder.guildeNametTv.text = name
        holder.guildePointsTv.text = points.toString()
    }

    override fun unbind(holder: GuildeViewHolder) {
        super.unbind(holder)
        glide.clear(holder.guildeLogoImg)
    }

    inner class GuildeViewHolder : KotlinEpoxyHolder() {
        val guildeLogoImg by bind<ImageView>(R.id.guildeLogoImg)
        val guildeNametTv by bind<TextView>(R.id.guildeNameTv)
        val guildePointsTv by bind<TextView>(R.id.guildePointsTv)
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