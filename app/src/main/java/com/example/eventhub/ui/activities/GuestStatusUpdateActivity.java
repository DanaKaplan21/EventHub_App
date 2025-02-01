package com.example.eventhub.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
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

public class GuestStatusUpdateActivity extends AppCompatActivity {
    private TextView tvGuestEmail;
    private Button btnConfirmed, btnDeclined;
    private String eventId, guestEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_status_update);

        tvGuestEmail = findViewById(R.id.tv_guest_email);
        btnConfirmed = findViewById(R.id.btn_attending);
        btnDeclined = findViewById(R.id.btn_not_attending);

        guestEmail = getIntent().getStringExtra("guest_email");
        eventId = getIntent().getStringExtra("event_id");

        tvGuestEmail.setText(guestEmail);

        btnConfirmed.setOnClickListener(v -> updateGuestStatus("Confirmed"));
        btnDeclined.setOnClickListener(v -> updateGuestStatus("Declined"));
    }

    private void updateGuestStatus(String status) {
        if (eventId == null || guestEmail == null) {
            Toast.makeText(this, "Error: Event ID or Email is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("status", status);

        apiService.updateGuestStatus(eventId, guestEmail, statusMap).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GuestStatusUpdateActivity.this, "Status updated to: " + status, Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(GuestStatusUpdateActivity.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GuestStatusUpdateActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
