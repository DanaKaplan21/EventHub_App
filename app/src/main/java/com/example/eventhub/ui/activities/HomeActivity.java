package com.example.eventhub.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventhub.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // מעבר למסך רישום אירועים
        findViewById(R.id.btn_register_event).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // מעבר למסך פרטי אירוע
        findViewById(R.id.btn_event_details).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EventDetailsActivity.class);
            startActivity(intent);
        });
    }
}
