package com.example.eventhub.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventhub.R;
import com.example.eventhub.models.Guest;
import com.example.eventhub.ui.activities.GuestStatusUpdateActivity;
import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {
    private final List<Guest> guestList;
    private final String eventId;
    private static final int REQUEST_CODE_UPDATE_STATUS = 1;

    public GuestAdapter(List<Guest> guestList, String eventId) {
        this.guestList = guestList;
        this.eventId = eventId;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        Guest guest = guestList.get(position);
        holder.tvEmail.setText(guest.getEmail());
        holder.tvStatus.setText(guest.getStatus());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), GuestStatusUpdateActivity.class);
            intent.putExtra("guest_email", guest.getEmail());
            intent.putExtra("event_id", eventId);
            ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_CODE_UPDATE_STATUS);
        });
    }

    @Override
    public int getItemCount() {
        return guestList.size();
    }

    public static class GuestViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvStatus;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tv_guest_email);
            tvStatus = itemView.findViewById(R.id.tv_guest_status);
        }
    }
}
