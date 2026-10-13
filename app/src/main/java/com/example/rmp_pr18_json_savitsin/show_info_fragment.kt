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
    private var tasks: MutableList<Task> = mutableListOf()
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

        tvTask = rootView.findViewById(R.id.tvTask) as TextView
        tvDate = rootView.findViewById(R.id.tvDate) as TextView
        tvPriority = rootView.findViewById(R.id.tvPriority) as TextView
        spinTasks = rootView.findViewById(R.id.spinTasks) as Spinner

        val gson = Gson()
        val json = pref.getString("tasks_json", "[]")
        val type = object : TypeToken<MutableList<Task>>() {}.type
        val loaded: MutableList<Task>? = gson.fromJson(json, type)
        if (loaded != null) {
            tasks = loaded
        }

        val names = ArrayList<String>()
        for (t in tasks) {
            names.add(t.task)
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            names
        )
        spinTasks.adapter = adapter

        spinTasks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val t = tasks[position]
                tvTask.text = "Задача: " + t.task
                tvDate.text = "Дата: " + t.date
                tvPriority.text = "Приоритет: " + t.priority
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val BackButton = rootView.findViewById<Button>(R.id.buttonBack)
        BackButton.setOnClickListener {
            btnBack()
        }

        return rootView
    }

    fun btnBack() {
        val mainActivity = activity as MainActivity
        mainActivity.go(input_info_fragment())
    }
}