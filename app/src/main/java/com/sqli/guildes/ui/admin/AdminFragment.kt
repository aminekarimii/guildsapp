package com.sqli.guildes.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.data.models.Submission
import com.sqli.guildes.ui.common.epoxy.controllers.SubmissionController
import com.sqli.guildes.ui.common.epoxy.interfaces.Callbacks
import kotlinx.android.synthetic.main.fragment_admin.*

class AdminFragment : Fragment() {

    /*companion object {
        fun newInstance() = AdminFragment()
    }*/

    private lateinit var adminViewModel: AdminViewModel
    private lateinit var submissionController: SubmissionController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : Callbacks {
            override fun onItemClicked(id: String, sharedView: View?) {
                AdminFragmentDirections.actionAdminFragmentToValidationFragment()
                        .apply {
                            contribId = id
                        }.also { findNavController().navigate(it) }
            }
        }

        rvAdminFragment.apply {
            layoutManager = LinearLayoutManager(context)
            submissionController = SubmissionController(context, callback)
            setController(submissionController)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adminViewModel = obtainViewModel(AdminViewModel::class.java).apply {
            submissions.observe(this@AdminFragment, Observer {
                submissionController.setData(it)
                if (it is Resource.Success)
                    setViews(it.data)
            })
        }
        adminViewModel.loadAllSubmissions()
    }

    private fun setViews(listSubmissions: List<Submission>) {
        tvContribTotal.text = listSubmissions.size.toString()
        invalidatedContibs.text = listSubmissions.count { !it.validated }.toString()
    }
}
