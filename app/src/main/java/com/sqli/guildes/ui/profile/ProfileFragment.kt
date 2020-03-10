package com.sqli.guildes.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.data.models.User
import com.sqli.guildes.ui.common.epoxy.controllers.SubmissionController
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import log

class ProfileFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var submissionController: SubmissionController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvProfileContributions.apply {
            layoutManager = LinearLayoutManager(context)
            submissionController = SubmissionController()
            setController(submissionController)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = (activity as MainActivity).obtainViewModel()
        mainViewModel.updateToolbarTitle("Profile")

        profileViewModel = obtainViewModel(ProfileViewModel::class.java).apply {

            currentContributor.observe(this@ProfileFragment, Observer {
                when (it) {
                    is Resource.Success -> with(it.data) {
                        setupTextViews(this)
                    }
                    is Resource.Error -> with(it.errorMessage) {
                        log(this)
                    }
                }
            })

            submissions.observe(this@ProfileFragment, Observer {
                log(it.toString())
                submissionController.setData(it)
            })
        }

        profileViewModel.loadCurrentUserDetails().loadCurrentUserSubmissions()
        //profileViewModel

    }

    private fun setupTextViews(data: User) {
        tvProfileFName.text = data.firstname
        tvProfileLName.text = data.lastname
        tvProfileGuildeName.text = data.guilde.name
        tvProfileGuildePoints.text = "${data.guilde.points}"
    }
}