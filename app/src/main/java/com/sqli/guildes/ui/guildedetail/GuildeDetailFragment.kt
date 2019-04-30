package com.sqli.guildes.ui.guildedetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqli.guildes.R

class GuildeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GuildeDetailFragment()
    }

    private lateinit var viewModel: GuildeDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guilde_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GuildeDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
