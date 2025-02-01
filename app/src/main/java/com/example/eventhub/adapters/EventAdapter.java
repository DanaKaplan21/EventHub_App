package com.example.eventhub.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.models.Event;
import com.example.eventhub.ui.activities.GuestListActivity;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final List<Event> eventList;
    private final Context context;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.eventTitle.setText(event.getTitle());
        holder.eventDate.setText(event.getDate());
        holder.eventDescription.setText(event.getDescription());
        holder.eventLocation.setText(event.getLocation());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), GuestListActivity.class);
            intent.putExtra("event_id", event.getId()); // וודא ש-getId() מחזיר ערך תקין

            Log.d("EventAdapter", "Event ID: " + event.getId());

            v.getContext().startActivity(intent);
        });

    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, eventDate, eventDescription, eventLocation;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title);
            eventDate = itemView.findViewById(R.id.event_date);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventLocation = itemView.findViewById(R.id.event_location);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
