package com.sqli.guildes.ui.contributions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqli.guildes.R

class ContributionsFragment : Fragment() {

    companion object {
        fun newInstance() = ContributionsFragment()
    }

    private lateinit var viewModel: ContributionsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contributions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContributionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
