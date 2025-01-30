package com.example.clbootstrap.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clbootstrap.R;
import com.example.clbootstrap.admin.ClubDetailsActivity;
import com.example.clbootstrap.models.Club;

import java.util.ArrayList;
import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {
    private List<Club> clubs;

    public ClubAdapter() {
        this.clubs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_club_card, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        holder.bind(clubs.get(position));
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public void addClub(Club club) {
        clubs.add(club);
        notifyItemInserted(clubs.size() - 1);
    }

    static class ClubViewHolder extends RecyclerView.ViewHolder {
        private final ImageView clubLogo;
        private final TextView clubName;

        ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            clubLogo = itemView.findViewById(R.id.clubLogo);
            clubName = itemView.findViewById(R.id.clubName);
        }

        void bind(Club club) {
            clubName.setText(club.getName());
            
            // Set default club logo
            clubLogo.setImageResource(R.drawable.ic_club_placeholder);
            
            // If we have a logo URI, try to set it
            if (club.getLogoUri() != null) {
                try {
                    clubLogo.setImageURI(club.getLogoUri());
                } catch (Exception e) {
                    // Keep default logo if there's an error
                    clubLogo.setImageResource(R.drawable.ic_club_placeholder);
                }
            }

            // Set click listener
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ClubDetailsActivity.class);
                intent.putExtra("club_name", club.getName());
                v.getContext().startActivity(intent);
            });
        }
    }
}
