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

        val priorities = arrayOf("Низкий", "Средний", "Высокий")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            priorities
        )
        priority.adapter = adapter

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

    fun btnAddTask() {
        if (taskInput.text.toString() != "" && date.text.toString() != "") {

            task = Task()
            task.task = taskInput.text.toString()
            task.date = date.text.toString()
            task.priority = priority.selectedItem.toString()

            val gson = Gson()
            val json = pref.getString("tasks_json", "[]")
            val type = object : TypeToken<MutableList<Task>>() {}.type
            var tasks: MutableList<Task> = gson.fromJson(json, type)
            if (tasks == null) {
                tasks = mutableListOf<Task>()
            }

            tasks.add(task)

            val newJson = gson.toJson(tasks)
            ed = pref.edit()
            ed.putString("tasks_json", newJson)
            ed.apply()

            taskInput.setText("")
            date.setText("")

            val mainActivity = activity as MainActivity
            mainActivity.go(show_info_fragment())
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
        val mainActivity = activity as MainActivity
        mainActivity.go(login_fragment())
    }
}