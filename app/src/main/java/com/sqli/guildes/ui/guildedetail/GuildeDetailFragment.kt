package com.sqli.guildes.ui.guildedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqli.guildes.R
import com.sqli.guildes.ui.main.MainActivity
import com.sqli.guildes.ui.main.MainViewModel

class GuildeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GuildeDetailFragment()
    }

    private lateinit var mainViewModel: MainViewModel

    private lateinit var guildeDetailviewModel: GuildeDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guilde_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = (activity as MainActivity).obtainViewModel()

        val id = arguments?.let { GuildeDetailFragmentArgs.fromBundle(it).guildeIdArg } ?: 10


    }

}
