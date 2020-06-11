package com.sqli.guildes.ui.myguild

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.ui.common.epoxy.controllers.SubmissionController
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_myguild.*

class MyGuildFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var myGuildViewModel: MyGuildViewModel
    private lateinit var submissionController: SubmissionController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_myguild, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMyGuildContribs.apply {
            layoutManager = LinearLayoutManager(context)
            submissionController = SubmissionController(context)
            setController(submissionController)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = (activity as MainActivity).obtainViewModel().apply {
            //updateToolbarTitle("Home")
            tvMyGuildName.text = currentUser?.guilde?.name
            tvMyGuildPts.text = currentUser?.guilde?.points.toString()
            tvMyGuildSite.text = currentUser?.site
        }

        myGuildViewModel = obtainViewModel(MyGuildViewModel::class.java).apply {
            guildContributions.observe(this@MyGuildFragment, Observer {
                submissionController.setData(it)
                when (it) {
                    is Resource.Success ->{
                        Log.d("TAG", "onActivityCreated: " + it.data)
                    }
                }
            })
        }

        myGuildViewModel.loadGuildContributions()
    }

}