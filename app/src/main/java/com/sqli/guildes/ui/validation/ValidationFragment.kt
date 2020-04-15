package com.sqli.guildes.ui.validation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.data.models.Submission
import kotlinx.android.synthetic.main.fragment_validation.*

class ValidationFragment : Fragment() {

    private lateinit var validationViewModel: ValidationViewModel

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
        }
        loadData()
    }

    private fun setView(data:Submission){
        tvContrib.text = data.createdBy.lastname
        tvGuildName.text = data.createdBy.guilde.name
        tvProfileGuildePoints.text = data.points.toString()
        tvContribTitle.text = data.subject
        tvContribDescription.text = data.description
        tvContribType.text = data.type

    }

    private fun loadData() {
        val contribId: String? = ValidationFragmentArgs.fromBundle(arguments!!).contribId

        contribId?.let {
            validationViewModel.getContribution(it)
        }
    }

}