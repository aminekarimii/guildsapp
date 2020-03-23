package com.sqli.guildes.ui.addsubmission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R
import com.sqli.guildes.core.extensions.obtainViewModel
import kotlinx.android.synthetic.main.fragment_addsubmission.*

class AddSubmissionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var addSubmissionViewModel: AddSubmissionViewModel
    private var points:Int = 0
    private var type:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_addsubmission, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addSubmissionViewModel = obtainViewModel(AddSubmissionViewModel::class.java).apply {
            spinnerOptions.observe(this@AddSubmissionFragment, Observer {
                it.also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    sp_types_submissions.adapter = adapter
                    sp_types_submissions.onItemSelectedListener = this@AddSubmissionFragment
                }
            })

            submission.observe(this@AddSubmissionFragment, Observer {
                if (it) { showSnackbar("Votre Contribution est ajoutée") }
            })

            message.observe(this@AddSubmissionFragment, Observer { showSnackbar(it) })
        }
        setupGuildView()
        addSubmissionViewModel.createSpinnerAdapter(context!!)
        btnSubmission.setOnClickListener { addSubmission() }

    }

    private fun addSubmission() {
        val subject = inputSubject.text.toString()
        val description = inputDescription.text.toString()
        val points = this.points
        val type = this.type
        addSubmissionViewModel.addSubmission(subject, type, description, points)
    }

    private fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(activity!!.findViewById(android.R.id.content), message, length).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.type = addSubmissionViewModel.getSubmissionType(position)
        this.points = addSubmissionViewModel.getSubmissionPoints(position)
    }

    private fun setupGuildView(){
        tvGuildeInfoName.text = addSubmissionViewModel.currentUser!!.guilde.name
        tvGuildInfoPoints.text = "${addSubmissionViewModel.currentUser!!.guilde.points} pt"
        ivGuildeInfo.setImageDrawable(
                resources.getDrawable(addSubmissionViewModel.getDrawableRes(context!!))
        )

    }
}