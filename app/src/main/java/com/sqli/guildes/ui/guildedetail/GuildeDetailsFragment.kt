package com.sqli.guildes.ui.guildedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.ui.common.epoxy.controllers.UserController
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_guilde_details.*
import kotlinx.android.synthetic.main.fragment_guilde_details.tvGuildeName

class GuildeDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = GuildeDetailsFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var guildeDetailsViewModel: GuildeDetailsViewModel
    private lateinit var contributorsControllers: UserController

    private lateinit var guildeDetailviewModel: GuildeDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guilde_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvContributors.apply {
            layoutManager = LinearLayoutManager(context)
            contributorsControllers = UserController()
            setController(contributorsControllers)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = (activity as MainActivity).obtainViewModel()

        mainViewModel.updateToolbarTitle("")


        guildeDetailsViewModel = obtainViewModel(GuildeDetailsViewModel::class.java).apply {

            guilde.observe(this@GuildeDetailsFragment, Observer { when (it) {
                is Resource.Success -> with(it.data) {
                    tvGuildeName.text = name
                    tvGuildePoints.text = "$points"
                }
            }})

            guildeContributors.observe(this@GuildeDetailsFragment, Observer {
                contributorsControllers.setData(it)
            })
        }

        loadData()

    }

    private fun loadData() {
        var guildeId : String? = GuildeDetailsFragmentArgs.fromBundle(arguments!!).guildeIdArg
        if (guildeId!!.isBlank()) {
            guildeId = mainViewModel.currentUser?.guilde?.id
        }

        guildeId?.let {
            guildeDetailsViewModel.loadGuildeDetails(it)
            guildeDetailsViewModel.loadGuildeContributors(it)
        }
    }

}
