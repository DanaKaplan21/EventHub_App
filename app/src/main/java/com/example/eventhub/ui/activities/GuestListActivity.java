package com.example.eventhub.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventhub.R;
import com.example.eventhub.adapters.GuestAdapter;
import com.example.eventhub.data.ApiClient;
import com.example.eventhub.models.Guest;
import com.example.eventhub.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestListActivity extends AppCompatActivity {
    private RecyclerView rvGuestList;
    private Button btnBack;
    private String eventId;
    private static final int REQUEST_CODE_UPDATE_STATUS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        rvGuestList = findViewById(R.id.rv_guests);
        btnBack = findViewById(R.id.btn_back_to_events);

        eventId = getIntent().getStringExtra("event_id");

        if (eventId != null) {
            fetchGuestList();
        } else {
            Log.e("GuestListActivity", "Event ID is null or empty!");
            Toast.makeText(this, "Event ID is missing!", Toast.LENGTH_SHORT).show();
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void fetchGuestList() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getGuestsByEvent(eventId).enqueue(new Callback<List<Guest>>() {
            @Override
            public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Guest> guestList = response.body();
                    GuestAdapter adapter = new GuestAdapter(guestList, eventId);
                    rvGuestList.setLayoutManager(new LinearLayoutManager(GuestListActivity.this));
                    rvGuestList.setAdapter(adapter);
                } else {
                    Toast.makeText(GuestListActivity.this, "לא נמצאו מוזמנים.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Guest>> call, Throwable t) {
                Toast.makeText(GuestListActivity.this, "שגיאה: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_STATUS && resultCode == RESULT_OK) {
            fetchGuestList();
        }
    }
}
