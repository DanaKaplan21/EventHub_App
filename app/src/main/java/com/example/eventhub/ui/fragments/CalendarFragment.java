package com.example.eventhub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.adapters.CalendarAdapter;
import com.example.eventhub.data.ApiClient;
import com.example.eventhub.models.Event;
import com.example.eventhub.network.ApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {

    private Calendar calendar;
    private TextView monthYearTextView;
    private RecyclerView recyclerView;
    private CalendarAdapter adapter;
    private List<Event> events = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        monthYearTextView = view.findViewById(R.id.monthYearTextView);
        recyclerView = view.findViewById(R.id.calendarRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7)); // 7 ימים בשבוע

        calendar = Calendar.getInstance();

        fetchEvents();

        view.findViewById(R.id.prevMonthButton).setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        view.findViewById(R.id.nextMonthButton).setOnClickListener(v -> {
            calendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        return view;
    }

    private void fetchEvents() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    events = response.body();
                    updateCalendar();
                } else {
                    Toast.makeText(getContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCalendar() {
        String monthYear = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                + " " + calendar.get(Calendar.YEAR);
        monthYearTextView.setText(monthYear);

        List<String> days = generateDaysForMonth(calendar);
        adapter = new CalendarAdapter(days, events, getContext());
        recyclerView.setAdapter(adapter);
    }

    private List<String> generateDaysForMonth(Calendar calendar) {
        List<String> days = new ArrayList<>();

        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < firstDayOfWeek; i++) {
            days.add("");
        }

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(String.format(Locale.getDefault(), "%02d-%02d-%04d",
                    i, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)));
        }

        return days;
    }
}
