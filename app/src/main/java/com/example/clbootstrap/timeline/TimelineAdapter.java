package com.example.clbootstrap.timeline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.clbootstrap.R;
import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<TimelinePost> posts;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TimelinePost post);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TimelineAdapter() {
        this.posts = new ArrayList<>();
        // Add single post
        posts.add(new TimelinePost(
            "Create your first club",
            "Create. Connect. Conquer.",
            "29-Jan-2025 at 11:59:57 pm",
            24,
            0
        ));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimelinePost post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView subtitleText;
        private final TextView timeText;
        private final TextView likeCount;
        private final TextView commentCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            subtitleText = itemView.findViewById(R.id.subtitleText);
            timeText = itemView.findViewById(R.id.timeText);
            likeCount = itemView.findViewById(R.id.likeCount);
            commentCount = itemView.findViewById(R.id.commentCount);

            // Set click listener for the entire item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && TimelineAdapter.this.listener != null) {
                    TimelineAdapter.this.listener.onItemClick(posts.get(position));
                }
            });
        }

        public void bind(TimelinePost post) {
            titleText.setText(post.getTitle());
            subtitleText.setText(post.getSubtitle());
            timeText.setText(post.getTime());
            likeCount.setText(String.valueOf(post.getLikes()));
            commentCount.setText(String.valueOf(post.getComments()));
        }
    }
}
