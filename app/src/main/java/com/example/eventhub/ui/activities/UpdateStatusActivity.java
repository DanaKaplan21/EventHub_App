package com.example.eventhub.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventhub.R;
import com.example.eventhub.data.ApiClient;
import com.example.eventhub.network.ApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateStatusActivity extends AppCompatActivity {

    private RadioGroup rgStatus;
    private Button btnSubmitStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        rgStatus = findViewById(R.id.rg_status);
        btnSubmitStatus = findViewById(R.id.btn_submit_status);

        // קבלת הנתונים מה-Intent
        String guestEmail = getIntent().getStringExtra("guest_email");
        String eventId = getIntent().getStringExtra("event_id");

        if (guestEmail == null || eventId == null) {
            Toast.makeText(this, "Missing guest email or event ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        btnSubmitStatus.setOnClickListener(v -> {
            int selectedId = rgStatus.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a status", Toast.LENGTH_SHORT).show();
                return;
            }

            String status = ((RadioButton) findViewById(selectedId)).getText().toString();

            updateGuestStatus(eventId, guestEmail, status);
        });
    }

    private void updateGuestStatus(String eventId, String email, String status) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("status", status);

        apiService.updateGuestStatus(eventId, email, statusMap).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateStatusActivity.this, "Status updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateStatusActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateStatusActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
