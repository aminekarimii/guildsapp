package com.sqli.guildes.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sqli.guildes.data.DataManager
import com.sqli.starterkitandroid.R

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dataManager = context?.let { DataManager(it) }
        val loginViewModelFactory = dataManager?.let { LoginViewModelFactory(it) }
        viewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)

        viewModel.startLogin()
    }

}
