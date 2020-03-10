package com.sqli.guildes.ui.common.epoxy.models

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.sqli.guildes.R
import com.sqli.guildes.ui.common.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_submission)
abstract class SubmissionModel : EpoxyModelWithHolder<SubmissionModel.ContributionViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute
    lateinit var subject: String
    @EpoxyAttribute
    lateinit var site: String

    override fun bind(holder: ContributionViewHolder) {
        super.bind(holder)
        holder.root.setOnClickListener(clickListener)
        holder.tvContributionName.text = subject
        holder.tvTypeContribution.text = site
    }

    inner class ContributionViewHolder : KotlinEpoxyHolder() {
        val root by bind<CardView>(R.id.itemContribution)
        val tvContributionName by bind<TextView>(R.id.tvContribution)
        val tvTypeContribution by bind<TextView>(R.id.tvTypeContribution)
    }
}
