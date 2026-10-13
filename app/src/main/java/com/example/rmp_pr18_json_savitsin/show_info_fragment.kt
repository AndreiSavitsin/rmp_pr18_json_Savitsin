package com.example.rmp_pr18_json_savitsin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment

class show_info_fragment : Fragment() {
    private lateinit var task: Task
    private lateinit var taskInput: EditText
    private lateinit var date: EditText
    private lateinit var priority: Spinner
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        task = Task()

        pref = requireActivity().getSharedPreferences("Tasks", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_input_info, container, false)

        taskInput = rootView.findViewById(R.id.taskInput) as EditText
        date = rootView.findViewById(R.id.dateInput) as EditText
        priority = rootView.findViewById(R.id.spinPriority) as Spinner

        val BackButton = rootView.findViewById<Button>(R.id.buttonBack)
        BackButton.setOnClickListener {
            btnBack()
        }

        return rootView
    }

    fun btnBack()
    {
        val intent = Intent(requireContext(), LoginFragment::class.java)
        startActivity(intent)
    }
}