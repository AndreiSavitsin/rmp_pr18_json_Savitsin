package com.example.rmp_pr18_json_savitsin

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class input_info_fragment : Fragment() {
    private lateinit var taskInput: EditText
    private lateinit var date: EditText
    private lateinit var priority: Spinner
    private lateinit var pref: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = requireActivity().getSharedPreferences("Tasks", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_input_info, container, false)

        taskInput = rootView.findViewById(R.id.taskInput)
        date = rootView.findViewById(R.id.dateInput)
        priority = rootView.findViewById(R.id.spinPriority)

        val priorities = arrayOf("Низкий", "Средний", "Высокий")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            priorities
        )
        priority.adapter = adapter

        rootView.findViewById<Button>(R.id.buttonAddTask).setOnClickListener {
            btnAddTask()
        }
        rootView.findViewById<Button>(R.id.buttonBack).setOnClickListener {
            btnBack()
        }

        return rootView
    }

    fun btnAddTask() {
        if (taskInput.text.toString() != "" && date.text.toString() != "") {
            val task = Task(
                task = taskInput.text.toString(),
                date = date.text.toString(),
                priority = priority.selectedItem.toString()
            )

            val json = pref.getString("tasks_json", "[]")
            val type = object : TypeToken<MutableList<Task>>() {}.type
            val tasks: MutableList<Task> = gson.fromJson(json, type) ?: mutableListOf()

            tasks.add(task)

            val newJson = gson.toJson(tasks)
            pref.edit().putString("tasks_json", newJson).apply()

            taskInput.text.clear()
            date.text.clear()

            (activity as MainActivity).go(show_info_fragment())
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.fieldsMustBeFilledIn))
                .setPositiveButton("OK", null)
                .create()
                .show()
        }
    }

    fun btnBack() {
        (activity as MainActivity).go(login_fragment())
    }
}