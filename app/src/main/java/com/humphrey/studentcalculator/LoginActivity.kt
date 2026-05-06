package com.humphrey.studentcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etStudentId = findViewById(R.id.etStudentId)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val studentId = etStudentId.text.toString().trim()
            if (studentId.isEmpty()) {
                Toast.makeText(this, "Please enter your Student ID", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CalculatorActivity::class.java)
                intent.putExtra("STUDENT_ID", studentId)
                startActivity(intent)
                finish()
            }
        }
    }
}