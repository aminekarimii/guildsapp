package com.sqli.guildes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqli.guildes.R
import com.sqli.guildes.core.Resource
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.ui.common.epoxy.controllers.GuildeController
import com.sqli.guildes.ui.common.epoxy.interfaces.Callbacks
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var guildesController: GuildeController
    private var isLess = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()

        setupViewAllTextVew()
    }

    private fun setupRecycleView() {
        val callbacks = object : Callbacks {
            override fun onItemClicked(id: String, sharedView: View?) {
                HomeFragmentDirections.actionHomeFragmentToGuildeDetailsFragment()
                        .apply {
                    guildeIdArg = id
                }.also { findNavController().navigate(it) }
            }
        }

        rvTopGuildes.apply {
            layoutManager = LinearLayoutManager(context)
            guildesController = GuildeController(callbacks)
            setController(guildesController)
        }
    }

    private fun setupViewAllTextVew() {
        tvViewAll.setOnClickListener {
            val tv = it as TextView
            if (isLess) tv.text = getText(R.string.view_less)
            else tv.text = getText(R.string.view_all)
            homeViewModel.loadGuildes()
            isLess = !isLess
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = (activity as MainActivity).obtainViewModel().apply {
            updateToolbarTitle("Home")
            tvHomeGuildeName.text = currentUser?.guilde?.name
            tvHomeGuildePoints.text = currentUser?.guilde?.points.toString()
        }

        homeViewModel = obtainViewModel(HomeViewModel::class.java).apply {
            guildes.observe(this@HomeFragment, Observer {
                guildesController.setData(
                        if (it is Resource.Success && isLess)
                            Resource.Success(it.data.take(3))
                        else it
                )
            })
        }

        homeViewModel.loadGuildes()
    }

}
