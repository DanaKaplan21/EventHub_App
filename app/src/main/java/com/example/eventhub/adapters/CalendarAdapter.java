package com.example.eventhub.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.models.Event;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {

    private final List<String> days;
    private final List<Event> events;
    private final Context context;

    public CalendarAdapter(List<String> days, List<Event> events, Context context) {
        this.days = days;
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String day = days.get(position);

        if (day.isEmpty()) {
            holder.dayTextView.setVisibility(View.GONE);
            holder.eventIcon.setVisibility(View.GONE);
        } else {
            holder.dayTextView.setVisibility(View.VISIBLE);
            holder.dayTextView.setText(day.split("-")[0]);
            List<Event> eventsOnDay = getEventsForDay(day);
            if (!eventsOnDay.isEmpty()) {
                holder.eventIcon.setVisibility(View.VISIBLE);
                holder.itemView.setOnClickListener(v -> showEventDetails(eventsOnDay));
            } else {
                holder.eventIcon.setVisibility(View.GONE);
            }
        }
    }

    private List<Event> getEventsForDay(String day) {
        List<Event> eventsOnDay = new ArrayList<>();
        for (Event event : events) {
            String eventDate = event.getDate().split(" ")[0];
            if (eventDate.equals(day)) {
                eventsOnDay.add(event);
            }
        }
        return eventsOnDay;
    }

    private void showEventDetails(List<Event> eventsOnDay) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Event Details");

        StringBuilder message = new StringBuilder();
        for (Event event : eventsOnDay) {
            message.append("Title: ").append(event.getTitle()).append("\n")
                    .append("Description: ").append(event.getDescription()).append("\n")
                    .append("Location: ").append(event.getLocation()).append("\n")
                    .append("Date: ").append(event.getDate()).append("\n\n");
        }

        builder.setMessage(message.toString());
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView;
        ImageView eventIcon;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            eventIcon = itemView.findViewById(R.id.eventIcon);
        }
    }
}
