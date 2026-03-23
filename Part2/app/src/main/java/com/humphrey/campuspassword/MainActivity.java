package com.humphrey.campuspassword;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.humphrey.campuspassword.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSubmit.setOnClickListener(v -> validateLogin());
    }

    private void validateLogin() {
        String studentId = binding.etStudentId.getText().toString();
        String password  = binding.etPassword.getText().toString();

        String lastTwo   = studentId.substring(studentId.length() - 2);
        String wallColor = "beige"; // change to actual lab wall color
        String expected  = wallColor + lastTwo;

        if (password.equals(expected)) {
            binding.tvResult.setText("Access granted!");
        } else {
            binding.tvResult.setText("Wrong password. Try again.");
        }
    }
}