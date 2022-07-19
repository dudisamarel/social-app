package com.colman.social_app.fragments.feedfragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colman.social_app.R;
import com.colman.social_app.entities.Post;

public class UsernameViewHolder extends RecyclerView.ViewHolder {
    Post currPost;
    ImageView postIV;
    Context context;

    public interface ViewHolderOnClickListener {
        void onclick(View view, Post post);
    }

    public UsernameViewHolder(Context context, @NonNull View itemView, UsernameViewHolder.ViewHolderOnClickListener clickListener) {
        super(itemView);
        this.context = context;
        postIV = itemView.findViewById(R.id.postIV);
        itemView.setOnClickListener(v -> {
            clickListener.onclick(v, currPost);
            //Log.i("ITEM_CLICK", title.getText().toString() + "was clicked");
        });
    }

    public void bind(Post post) {
        currPost = post;
        if (!post.getAttachmentURI().isEmpty())
            Glide.with(context).load(post.getAttachmentURI()).into(postIV);
    }
}
