package com.example.rmp_pr18_json_savitsin

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class login_fragment : Fragment() {
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = requireActivity().getSharedPreferences("Tasks", MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        login = rootView.findViewById(R.id.loginInput) as EditText
        password = rootView.findViewById(R.id.passInput) as EditText

        val loginButton = rootView.findViewById<Button>(R.id.btnLog)
        loginButton.setOnClickListener {
            btnLogin()
        }

        return rootView
    }

    private fun btnLogin() {
        if (login.text.toString() != "" && password.text.toString() != "") {
            val ed = pref.edit()
            ed.putString("user_login", login.text.toString())
            ed.apply()

            val activity = activity as MainActivity
            activity.go(input_info_fragment())
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.fieldsMustBeFilledIn))
                .setPositiveButton("OK", null)
                .create()
                .show()
        }
    }
}