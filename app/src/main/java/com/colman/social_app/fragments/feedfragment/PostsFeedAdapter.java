package com.colman.social_app.fragments.feedfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.social_app.R;
import com.colman.social_app.entities.Post;

import java.util.List;

public class PostsFeedAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private final PostViewHolder.ViewHolderOnClickListener clickListener;
    private List<Post> data = null;

    public PostsFeedAdapter(PostViewHolder.ViewHolderOnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        return new PostViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null)
        return data.size();

        return 0;
    }

    public void setData(List<Post> newData) {
        data = newData;
        notifyDataSetChanged();
    }
}
