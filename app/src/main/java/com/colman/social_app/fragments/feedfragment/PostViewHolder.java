package com.colman.social_app.fragments.feedfragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.social_app.R;
import com.colman.social_app.entities.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    Post currPost;
    TextView title;
    TextView content;

    public interface ViewHolderOnClickListener {
        void onclick(View view, Post post);
    }

    public PostViewHolder(@NonNull View itemView, ViewHolderOnClickListener clickListener) {
        super(itemView);
        title = itemView.findViewById(R.id.post_item_title);
        content = itemView.findViewById(R.id.post_item_content);
        itemView.setOnClickListener(v -> {
            clickListener.onclick(v, currPost);
            //Log.i("ITEM_CLICK", title.getText().toString() + "was clicked");
        });
    }

    public void bind(Post post) {
        currPost = post;
        title.setText(post.getTitle());
        content.setText(post.getContent());
    }
}
