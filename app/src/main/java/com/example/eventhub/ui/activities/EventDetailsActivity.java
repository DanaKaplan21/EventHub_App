package com.example.eventhub.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventhub.R;
import com.example.eventhub.adapters.GuestAdapter;
import com.example.eventhub.data.ApiClient;
import com.example.eventhub.models.Event;
import com.example.eventhub.models.Guest;
import com.example.eventhub.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends AppCompatActivity {
    private TextView tvTitle, tvDescription, tvDate, tvLocation;
    private RecyclerView rvGuests;
    private Button btnEditEvent;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        tvTitle = findViewById(R.id.tv_event_title);
        tvDescription = findViewById(R.id.tv_event_description);
        tvDate = findViewById(R.id.tv_event_date);
        tvLocation = findViewById(R.id.tv_event_location);
        rvGuests = findViewById(R.id.rv_event_guests);
        btnEditEvent = findViewById(R.id.btn_edit_event);

        eventId = getIntent().getStringExtra("event_id");

        if (eventId != null) {
            loadEventDetails(eventId);
        } else {
            Toast.makeText(this, "Event ID is missing", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnEditEvent.setOnClickListener(v -> {
            // כאן ניתן להוסיף מעבר למסך עריכת אירוע
        });
    }

    private void loadEventDetails(String eventId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getEvent(eventId).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Event event = response.body();
                    tvTitle.setText(event.getTitle());
                    tvDescription.setText(event.getDescription());
                    tvDate.setText(event.getDate());
                    tvLocation.setText(event.getLocation());

                    loadGuests(eventId);
                } else {
                    Toast.makeText(EventDetailsActivity.this, "Failed to load event details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(EventDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGuests(String eventId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getGuestsByEvent(eventId).enqueue(new Callback<List<Guest>>() {
            @Override
            public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Guest> guests = response.body();
                    GuestAdapter adapter = new GuestAdapter(guests, eventId); // כאן מתוקן
                    rvGuests.setLayoutManager(new LinearLayoutManager(EventDetailsActivity.this));
                    rvGuests.setAdapter(adapter);
                } else {
                    Toast.makeText(EventDetailsActivity.this, "Failed to load guests", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Guest>> call, Throwable t) {
                Toast.makeText(EventDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
