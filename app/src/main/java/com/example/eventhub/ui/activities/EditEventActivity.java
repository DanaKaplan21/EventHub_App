package com.example.eventhub.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.adapters.InviteeAdapter;
import com.example.eventhub.data.ApiClient;
import com.example.eventhub.models.Event;
import com.example.eventhub.models.Guest;
import com.example.eventhub.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEventActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDate, etLocation, etInviteEmail;
    private Button btnAddInvitee, btnSaveEvent;
    private RecyclerView inviteeRecyclerView;
    private InviteeAdapter inviteeAdapter;
    private List<Guest> invitees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        etTitle = findViewById(R.id.et_event_title);
        etDescription = findViewById(R.id.et_event_description);
        etDate = findViewById(R.id.et_event_date);
        etLocation = findViewById(R.id.et_event_location);
        etInviteEmail = findViewById(R.id.et_invite_email);
        btnAddInvitee = findViewById(R.id.btn_add_invitee);
        btnSaveEvent = findViewById(R.id.btn_save_event);
        inviteeRecyclerView = findViewById(R.id.inviteeRecyclerView);

        invitees = new ArrayList<>();
        inviteeAdapter = new InviteeAdapter(invitees);
        inviteeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inviteeRecyclerView.setAdapter(inviteeAdapter);

        btnAddInvitee.setOnClickListener(v -> {
            String email = etInviteEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                invitees.add(new Guest(email, "Invited"));
                inviteeAdapter.notifyDataSetChanged();
                etInviteEmail.setText("");
            } else {
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            }
        });

        btnSaveEvent.setOnClickListener(v -> saveEvent());
    }

    private void saveEvent() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(null, date, description, location, "OrganizerEmail", title, invitees);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.createEvent(event).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditEventActivity.this, "Event saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditEventActivity.this, "Failed to save event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditEventActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
