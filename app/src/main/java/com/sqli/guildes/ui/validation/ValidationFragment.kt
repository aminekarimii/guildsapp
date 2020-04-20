package com.sqli.guildes.ui.validation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.data.models.Submission
import kotlinx.android.synthetic.main.fragment_validation.*

class ValidationFragment : Fragment() {

    private lateinit var validationViewModel: ValidationViewModel
    private lateinit var contribId: String

            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_validation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        validationViewModel = obtainViewModel(ValidationViewModel::class.java).apply {
            contribution.observe(this@ValidationFragment, Observer {
                when(it){
                    is Resource.Success -> setView(it.data)
                }
            })

            validation.observe(this@ValidationFragment, Observer {
                if (it)
                    showSnackbar(R.id.validationView, R.string.validate_success_msg)
            })
        }
        loadData()
        btnValidate.setOnClickListener { validate() }
    }

    private fun setView(data:Submission){
        tvContrib.text = "${data.createdBy.lastname} ${data.createdBy.firstname}"
        tvProfileGuildePoints.text = data.points.toString()
        tvContribTitle.text = data.subject
        tvContribDescription.text = data.description
        tvContribType.text = data.type
        tvGuildeInfoName.text = data.createdBy.guilde.name
    }

    private fun loadData() {
        contribId = ValidationFragmentArgs.fromBundle(arguments!!).contribId

        validationViewModel.getContribution(contribId)

    }

    private fun validate(){
        validationViewModel.validateSubmission(contribId)
    }

    private fun showSnackbar(viewRes: Int, message: Int, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(activity!!.findViewById(viewRes), message, length).show()
    }

}