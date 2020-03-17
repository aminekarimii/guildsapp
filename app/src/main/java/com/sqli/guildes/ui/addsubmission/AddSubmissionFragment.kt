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
            submission.observe(this@AddSubmissionFragment, Observer {
                if (it) {
                    showSnackbar("Votre contribution est ajoutee")
                }
            })

            message.observe(this@AddSubmissionFragment, Observer { showSnackbar(it) })
        }
        btnSubmission.setOnClickListener { addSubmission() }
    }

    fun addSubmission() {
        val subject = inputSubject.text.toString()
        addSubmissionViewModel.addSubmission(subject)
    }

    private fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
        //TODO Fix this snackbar
        //Snackbar.make(view!!.findViewById(android.R.id.content), message, length).show()
    }
}