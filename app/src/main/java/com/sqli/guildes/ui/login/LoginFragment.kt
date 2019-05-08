package com.sqli.guildes.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sqli.guildes.R
import com.sqli.guildes.core.extensions.onRightDrawableClicked
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginViewModel = (activity as LoginActivity).obtainViewModel()

        inputUsername.onRightDrawableClicked { it.text.clear() }
        btnLogin.setOnClickListener { login() }

    }

    fun login() {
        val username : String = inputUsername.text.toString()
        val password : String = inputPassword.text.toString()

        loginViewModel.getRequestToken(username, password)
    }
}
