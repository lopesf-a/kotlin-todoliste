package com.example.todolist

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {
    private val tasks = mutableListOf<String>()
    private lateinit var taskInput: EditText
    private lateinit var addButton: Button
    private lateinit var taskList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView = ScrollView(this)
        rootView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val container = LinearLayout(this)
        container.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        container.orientation = LinearLayout.VERTICAL
        container.setPadding(16, 16, 16, 16) // Ajout de padding

        taskInput = EditText(this)
        taskInput.hint = "Nouvelle tâche"
        taskInput.setPadding(8, 8, 8, 8) // Ajout de padding
        container.addView(taskInput)

        addButton = Button(this)
        addButton.text = "Ajouter"
        addButton.setPadding(8, 8, 8, 8) // Ajout de padding
        addButton.setOnClickListener { addTask() }
        container.addView(addButton)

        taskList = LinearLayout(this)
        taskList.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        taskList.orientation = LinearLayout.VERTICAL
        container.addView(taskList)

        rootView.addView(container)
        setContentView(rootView)
    }

    private fun addTask() {
        val newTask = taskInput.text.toString()
        if (newTask.isNotEmpty()) {
            tasks.add(newTask)
            displayTasks()
            taskInput.text.clear()
        }
    }

    private fun displayTasks() {
        taskList.removeAllViews()
        for (task in tasks) {
            val taskTextView = TextView(this)
            taskTextView.text = task
            taskTextView.setPadding(0, 8, 0, 8) // Ajout de padding

            val editButton = Button(this)
            editButton.text = "Modifier"
            editButton.setPadding(8, 4, 8, 4) // Ajout de padding
            editButton.setOnClickListener { editTask(task) }

            val deleteButton = Button(this)
            deleteButton.text = "Supprimer"
            deleteButton.setPadding(8, 4, 8, 4) // Ajout de padding
            deleteButton.setOnClickListener { deleteTask(task) }

            val taskContainer = LinearLayout(this)
            taskContainer.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            taskContainer.orientation = LinearLayout.HORIZONTAL
            taskContainer.setPadding(0, 8, 0, 8) // Ajout de padding

            taskContainer.addView(taskTextView)
            taskContainer.addView(editButton)
            taskContainer.addView(deleteButton)

            taskList.addView(taskContainer)
        }
    }

    private fun editTask(task: String) {
        val taskIndex = tasks.indexOf(task)
        if (taskIndex >= 0) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Modifier la tâche")

            val input = EditText(this)
            input.setText(task)
            builder.setView(input)

            builder.setPositiveButton("Valider") { _, _ ->
                val updatedTask = input.text.toString()
                tasks[taskIndex] = updatedTask
                displayTasks()
            }

            builder.setNegativeButton("Annuler") { _, _ -> }

            builder.show()
        }
    }

    private fun deleteTask(task: String) {
        tasks.remove(task)
        displayTasks()
    }
}
