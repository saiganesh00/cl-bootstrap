package com.example.clbootstrap.adapters;

import android.content.Intent;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club_card, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.bind(club);
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
        ImageView clubLogo;
        TextView clubName;

        ClubViewHolder(View itemView) {
            super(itemView);
            clubLogo = itemView.findViewById(R.id.clubLogo);
            clubName = itemView.findViewById(R.id.clubName);
        }

        void bind(Club club) {
            clubName.setText(club.getName());
            if (club.getLogoUri() != null) {
                clubLogo.setImageURI(club.getLogoUri());
            }

            // Set click listener
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), ClubDetailsActivity.class);
                intent.putExtra("club_name", club.getName());
                intent.putExtra("club_logo", club.getLogoUri().toString());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
