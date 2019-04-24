package com.sqli.guildes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sqli.guildes.R
import com.sqli.guildes.core.extensions.obtainViewModel
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var epoxyController: HomeController

    private val callbacks = object : HomeController.Callbacks {
        override fun onGuildeItemClicked(id: String, transitionName: String, sharedView: View?) = Unit
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glideRequestManager : RequestManager = Glide.with(this)
        epoxyController = HomeController(callbacks, glideRequestManager)
        rvTopGuildes.apply {
            layoutManager = LinearLayoutManager(context)
            setController(epoxyController)
        }

        tvViewAll.setOnClickListener { val tv = it as TextView
            if(tv.text == getText(R.string.view_all)) {
                homeViewModel.loadGuildes(false)
                tv.text = getText(R.string.view_less)
            } else {
                homeViewModel.loadGuildes(true)
                tv.text = getText(R.string.view_all)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = (activity as MainActivity).obtainViewModel()

        homeViewModel = obtainViewModel(HomeViewModel::class.java).apply {
            guildes.observe(this@HomeFragment, Observer {
                epoxyController.setData(it)
            })
        }

        homeViewModel.loadGuildes(true)
    }

}
