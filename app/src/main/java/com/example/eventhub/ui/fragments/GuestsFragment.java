package com.example.eventhub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class GuestsFragment extends Fragment {
    private static final String ARG_EVENT_ID = "event_id";
    private String eventId;

    public static GuestsFragment newInstance(String eventId) {
        GuestsFragment fragment = new GuestsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getString(ARG_EVENT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guests, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.guestsRecyclerView);

        if (eventId != null) {
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            apiService.getGuestsByEvent(eventId).enqueue(new Callback<List<Guest>>() {
                @Override
                public void onResponse(Call<List<Guest>> call, Response<List<Guest>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Guest> guests = response.body();
                        GuestAdapter adapter = new GuestAdapter(guests, eventId);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch guests", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Guest>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Event ID is missing", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
