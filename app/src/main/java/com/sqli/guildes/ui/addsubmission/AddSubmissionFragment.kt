package com.sqli.guildes.ui.addsubmission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R
import com.sqli.guildes.core.extensions.obtainViewModel
import kotlinx.android.synthetic.main.fragment_addsubmission.*

class AddSubmissionFragment : Fragment() {

    private lateinit var addSubmissionViewModel: AddSubmissionViewModel

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
                }
            })

            submission.observe(this@AddSubmissionFragment, Observer {
                if (it) {
                    showSnackbar("Votre Contribution est ajoutée")
                }
            })

            message.observe(this@AddSubmissionFragment, Observer { showSnackbar(it) })
        }
        addSubmissionViewModel.createSpinnerAdapter(context!!)
        btnSubmission.setOnClickListener { addSubmission() }

    }

    private fun addSubmission() {
        val subject = inputSubject.text.toString()
        val description = inputDescription.text.toString()
        val points = 1
        var type = ""
        if (checkSelectedItem()){ type = sp_types_submissions.selectedItem.toString()}
        addSubmissionViewModel.addSubmission(subject, type, description, points)
    }

    private fun checkSelectedItem(): Boolean {
        if (sp_types_submissions.selectedItemPosition == 0) {
            showSnackbar("Veuillez choisir un type de contribution !")
        }
        return true
    }

    private fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(activity!!.findViewById(android.R.id.content), message, length).show()
    }
}