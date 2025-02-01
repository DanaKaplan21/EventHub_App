package com.example.eventhub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventhub.R;
import com.example.eventhub.models.Guest;

import java.util.List;

public class InviteeAdapter extends RecyclerView.Adapter<InviteeAdapter.InviteeViewHolder> {
    private final List<Guest> invitees;

    public InviteeAdapter(List<Guest> invitees) {
        this.invitees = invitees;
    }

    @NonNull
    @Override
    public InviteeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest, parent, false);
        return new InviteeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteeViewHolder holder, int position) {
        Guest guest = invitees.get(position);
        holder.tvEmail.setText(guest.getEmail());
        holder.tvStatus.setText(guest.getStatus());
    }

    @Override
    public int getItemCount() {
        return invitees.size();
    }

    public static class InviteeViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvStatus;

        public InviteeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tv_guest_email);
            tvStatus = itemView.findViewById(R.id.tv_guest_status);
        }
    }
}
