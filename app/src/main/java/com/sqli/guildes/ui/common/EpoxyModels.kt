package com.sqli.guildes.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sqli.guildes.R

@EpoxyModelClass(layout = R.layout.item_guilde)
abstract class GuildeModel : EpoxyModelWithHolder<GuildeModel.GuildeViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute lateinit var transitionName: String
    @EpoxyAttribute lateinit var glide: RequestManager
    @EpoxyAttribute lateinit var name: String
    @EpoxyAttribute lateinit var description : String
    @EpoxyAttribute var points : Int = 0

    override fun bind(holder: GuildeViewHolder) {
        super.bind(holder)
        glide.load("file:///android_asset/1.jpg")
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply {
                    RequestOptions().placeholder(R.drawable.ph_guilde)
                            .error(R.drawable.ph_guilde)
                            .fallback(R.drawable.ph_guilde)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                .into(holder.guildeLogoImg)
        ViewCompat.setTransitionName(holder.guildeLogoImg, transitionName)

        holder.guildeLogoImg.setOnClickListener(clickListener)
        holder.guileNametTv.text = name
        holder.guildeDescTv.text = description
        holder.guildePointsTv.text = points.toString()
    }

    override fun unbind(holder: GuildeViewHolder) {
        super.unbind(holder)
        glide.clear(holder.guildeLogoImg)
    }

    inner class GuildeViewHolder : KotlinEpoxyHolder() {
        val guildeLogoImg by bind<ImageView>(R.id.guildeLogoImg)
        val guileNametTv by bind<TextView>(R.id.guildeNameTv)
        val guildeDescTv by bind<TextView>(R.id.guildeDescTv)
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