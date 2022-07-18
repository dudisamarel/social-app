package com.colman.social_app.fragments.postDetailFragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.fragments.feedfragment.PostsFeedViewModel;
import com.colman.social_app.fragments.newPostFragment.AddPostFragmentArgs;
import com.colman.social_app.fragments.newPostFragment.NewPostViewModel;

public class PostDetailsFragment extends Fragment {

    private PostDetailsViewModel postDetailsViewModel;
    private String postID = "";
    private VideoView attachmentVV;
    private ImageView attachmentIV;
    private TextView postTitle;
    private TextView postContent;


    public PostDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.postID = AddPostFragmentArgs.fromBundle(getArguments()).getPostID();
        attachmentVV = view.findViewById(R.id.attachmentVV);
        attachmentIV = view.findViewById(R.id.attachmentIV);
        ViewModelFactory viewModelFactory = ((SocialApplication) getActivity().getApplication()).getViewModelFactory();
        postDetailsViewModel = new ViewModelProvider(this, viewModelFactory).get(PostDetailsViewModel.class);
        postTitle = view.findViewById(R.id.postDetailTitle);
        postContent = view.findViewById(R.id.postDetailContent);
        postDetailsViewModel.getPostByID(postID).observe(getViewLifecycleOwner(), post -> {
            postTitle.setText(post.getTitle());
            postContent.setText(post.getContent());
            String attachmentString = post.getAttachmentURI();
            if (!attachmentString.isEmpty()) {
                Uri attachmentUri = Uri.parse(attachmentString);
                if (attachmentUri.toString().toLowerCase().contains("mp4")) {
                    setVideo(attachmentUri);
                } else {
                    setImage(attachmentUri);
                }
            }
        });


    }

    private void setImage(Uri uri) {
        attachmentVV.setVisibility(View.GONE);
        attachmentIV.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(uri).into(attachmentIV);
    }

    private void setVideo(Uri uri) {
        attachmentVV.setVideoURI(uri);
        MediaController mediaController = new MediaController(this.getContext());
        mediaController.setMediaPlayer(attachmentVV);
        mediaController.setAnchorView(attachmentVV);
        attachmentVV.setVisibility(View.VISIBLE);
        attachmentVV.setMediaController(mediaController);
        attachmentVV.setOnPreparedListener(mp -> {
            attachmentIV.setVisibility(View.GONE);
            mediaController.show();
            attachmentVV.start();
        });
    }

}
