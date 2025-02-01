package com.example.eventhub.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.adapters.EventAdapter;
import com.example.eventhub.data.ApiClient;
import com.example.eventhub.models.Event;
import com.example.eventhub.network.ApiService;
import com.example.eventhub.ui.activities.EditEventActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button addEventButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = view.findViewById(R.id.eventsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addEventButton = view.findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditEventActivity.class);
            startActivity(intent);
        });

        fetchEvents();
        return view;
    }

    private void fetchEvents() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Event> events = response.body();
                    Log.d("EventListFragment", "Events received: " + events.size());

                    if (events.isEmpty()) {
                        Log.e("EventListFragment", "No events found!");
                        Toast.makeText(getContext(), "No events available", Toast.LENGTH_SHORT).show();
                    }

                    EventAdapter adapter = new EventAdapter(getContext(), events);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("EventListFragment", "Failed to fetch events, response: " + response.errorBody());
                    Toast.makeText(getContext(), "Failed to fetch events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("EventListFragment", "Error fetching events: " + t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    }
