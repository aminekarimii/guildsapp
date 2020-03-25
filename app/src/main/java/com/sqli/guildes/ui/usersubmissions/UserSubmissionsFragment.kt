package com.sqli.guildes.ui.usersubmissions

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
import kotlinx.android.synthetic.main.fragment_user_contributions.*

class UserSubmissionsFragment : Fragment() {


    private lateinit var submissionController: SubmissionController
    private lateinit var userSubmissionsViewModel: UserSubmissionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_user_contributions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUserContributions.apply {
            layoutManager = LinearLayoutManager(context)
            submissionController = SubmissionController()
            setController(submissionController)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userSubmissionsViewModel = obtainViewModel(UserSubmissionsViewModel::class.java).apply {

            contributions.observe(this@UserSubmissionsFragment, Observer {
                when (it) {
                    is Resource.Success -> {
                        submissionController.setData(it)
                    }
                }
            })

            user.observe(this@UserSubmissionsFragment, Observer {
                when (it){
                    is Resource.Success -> with(it.data){
                        setupView(this)
                    }
                }
            })
        }

        loadData()
    }

    private fun setupView(user:User){
        tvUContribName.text = user.firstname
        tvUContribLName.text = user.lastname
    }

    private fun loadData() {
        val userId: String? = UserSubmissionsFragmentArgs.fromBundle(arguments!!).userIdArg

        userId?.let {
            userSubmissionsViewModel.getUserContributions(userId = it)
            userSubmissionsViewModel.getUserById(userId = it)
        }
    }
}