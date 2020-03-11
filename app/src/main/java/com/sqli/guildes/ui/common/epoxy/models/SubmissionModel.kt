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
abstract class SubmissionModel : EpoxyModelWithHolder<SubmissionModel.SubmissionViewHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    @EpoxyAttribute
    lateinit var subject: String
    @EpoxyAttribute
    lateinit var creationDate: String

    override fun bind(holder: SubmissionViewHolder) {
        super.bind(holder)
        holder.root.setOnClickListener(clickListener)
        holder.tvSubmissionSubject.text = subject
        holder.tvTimeSubmission.text = creationDate
    }

    inner class SubmissionViewHolder : KotlinEpoxyHolder() {
        val root by bind<CardView>(R.id.itemContribution)
        val tvSubmissionSubject by bind<TextView>(R.id.tvSubmissionSubject)
        val tvTimeSubmission by bind<TextView>(R.id.tvTimeSubmission)
    }
}
