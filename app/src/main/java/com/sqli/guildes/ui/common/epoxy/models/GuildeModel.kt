package com.sqli.guildes.ui.common.epoxy.models

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.sqli.guildes.R
import com.sqli.guildes.ui.common.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_guilde)
abstract class GuildeModel : EpoxyModelWithHolder<GuildeModel.GuildeViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute
    lateinit var name: String
    @EpoxyAttribute
    var points : Int = 0

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