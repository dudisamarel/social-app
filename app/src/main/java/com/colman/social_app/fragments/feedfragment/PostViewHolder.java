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
import com.squareup.picasso.Picasso;

public class PostViewHolder extends RecyclerView.ViewHolder {
    Post currPost;
    ImageView postIV;
    Context context;

    public interface ViewHolderOnClickListener {
        void onclick(View view, Post post);
    }

    public PostViewHolder(Context context, @NonNull View itemView, ViewHolderOnClickListener clickListener) {
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
        if (post.getAttachmentURI() != null)
            Glide.with(context).load(post.getAttachmentURI()).into(postIV);
    }
}
