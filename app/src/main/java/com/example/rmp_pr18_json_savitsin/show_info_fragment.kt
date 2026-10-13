package com.example.rmp_pr18_json_savitsin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class show_info_fragment : Fragment() {
    private lateinit var pref: SharedPreferences
    private val gson = Gson()
    private lateinit var tasks: MutableList<Task>
    private lateinit var tvTask: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvPriority: TextView
    private lateinit var spinTasks: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = requireActivity().getSharedPreferences("Tasks", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_show_info, container, false)

        tvTask = rootView.findViewById(R.id.tvTask)
        tvDate = rootView.findViewById(R.id.tvDate)
        tvPriority = rootView.findViewById(R.id.tvPriority)
        spinTasks = rootView.findViewById(R.id.spinTasks)

        val json = pref.getString("tasks_json", "[]")
        val type = object : TypeToken<MutableList<Task>>() {}.type
        tasks = gson.fromJson(json, type) ?: mutableListOf()

        val names = tasks.map { it.task }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            names
        )
        spinTasks.adapter = adapter

        if (tasks.isNotEmpty()) {
            showTask(0)
        }

        spinTasks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                showTask(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        rootView.findViewById<Button>(R.id.buttonBack).setOnClickListener {
            (activity as MainActivity).go(input_info_fragment())
        }

        return rootView
    }

    private fun showTask(position: Int) {
        val t = tasks[position]
        tvTask.text = "Задача: ${t.task}"
        tvDate.text = "Дата: ${t.date}"
        tvPriority.text = "Приоритет: ${t.priority}"
    }
}