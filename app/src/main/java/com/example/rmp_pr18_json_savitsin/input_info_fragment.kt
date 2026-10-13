package com.example.rmp_pr18_json_savitsin

import android.app.AlertDialog
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

class input_info_fragment : Fragment() {
    private lateinit var task: Task
    private lateinit var taskInput: EditText
    private lateinit var date: EditText
    private lateinit var priority: Spinner
    private lateinit var pref: SharedPreferences
    private lateinit var ed: SharedPreferences.Editor

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

        val AddButton = rootView.findViewById<Button>(R.id.buttonAddTask)
        AddButton.setOnClickListener {
            btnAddTask()
        }

        val BackButton = rootView.findViewById<Button>(R.id.buttonBack)
        BackButton.setOnClickListener {
            btnBack()
        }

        return rootView
    }

    fun btnAddTask()
    {
        if (taskInput.text.toString() != "" && date.text.toString() != "" && priority.toString() != "")
        {
            ed = pref.edit()
            ed.putString("taskInput", taskInput.text.toString())
            ed.putString("date", date.text.toString())
            ed.putString("priority", priority.toString())
            ed.apply()

            val intent = Intent(requireContext(), ShowInfoFragment::class.java)
            startActivity(intent)
        }
        else
        {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.fieldsMustBeFilledIn))
                .setPositiveButton("OK", null)
                .create()
                .show()
        }
    }
    fun btnBack()
    {
        val intent = Intent(requireContext(), LoginFragment::class.java)
        startActivity(intent)
    }
}