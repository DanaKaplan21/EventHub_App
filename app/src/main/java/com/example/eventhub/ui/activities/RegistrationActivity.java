package com.example.eventhub.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventhub.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName, etEmail;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.et_name); // שדה לשם
        etEmail = findViewById(R.id.et_email); // שדה לאימייל
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please enter your name and email", Toast.LENGTH_SHORT).show();
            } else {
                saveUserToPreferences(name, email);
                navigateToMainActivity();
            }
        });
    }

    private void saveUserToPreferences(String name, String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("EventHubPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", name); // שמירת שם המשתמש
        editor.putString("user_email", email); // שמירת אימייל המשתמש
        editor.apply();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
