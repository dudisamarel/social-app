package com.colman.social_app.fragments.feedfragment;

import android.app.Application;
import android.content.Context;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.colman.social_app.R;
import com.colman.social_app.entities.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    Post currPost;
    ImageView postIV;
    TextView postTV;
    Context context;

    public interface ViewHolderOnClickListener {
        void onclick(View view, Post post);
    }

    public PostViewHolder(Context context, @NonNull View itemView, ViewHolderOnClickListener clickListener) {
        super(itemView);
        this.context = context;
        postIV = itemView.findViewById(R.id.postIV);
        postTV = itemView.findViewById(R.id.postTV);

        itemView.setOnClickListener(v -> {
            clickListener.onclick(v, currPost);
        });
    }

    public void bind(Post post) {
        currPost = post;
        if (postTV != null)
            postTV.setText(post.getTitle());
        if (postIV != null && post.getAttachmentURI() != null && !post.getAttachmentURI().isEmpty())
            Glide.with(context).load(post.getAttachmentURI()).into(postIV);
    }
}
