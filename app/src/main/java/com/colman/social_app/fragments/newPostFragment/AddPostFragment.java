package com.colman.social_app.fragments.newPostFragment;

import static android.app.Activity.RESULT_OK;
import static com.colman.social_app.constants.Constants.PICK_MEDIA_REQUEST_CODE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.entities.Post;
import com.colman.social_app.services.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POST_ID = "";

    // TODO: Rename and change types of parameters
    private String postID = "";

    private NewPostViewModel newPostViewModel;

    private Button saveButton;
    private Button deleteButton;
    private Uri attachmentUriToPost;
    private EditText postTitle;
    private EditText postContent;
    private String mediaName;
    private ImageView attachmentIV;

    public AddPostFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.postID = AddPostFragmentArgs.fromBundle(getArguments()).getPostID();

        ViewModelFactory viewModelFactory = ((SocialApplication) getActivity().getApplication()).getViewModelFactory();
        newPostViewModel = new ViewModelProvider(this, viewModelFactory).get(NewPostViewModel.class);

        saveButton = view.findViewById(R.id.save_button);
        deleteButton = view.findViewById(R.id.delete_button);
        postTitle = view.findViewById(R.id.postTitle);
        postContent = view.findViewById(R.id.postContent);
        attachmentIV = view.findViewById(R.id.attachmentIV);

        view.findViewById(R.id.addAAttachmentButton).setOnClickListener(v -> {
            ImageUtils.selectImageOrVideo(this.getContext());
        });

        // new post - no getting post details is needed
        if (postID.equals("")) {
            deleteButton.setVisibility(View.GONE);
            saveButton.setOnClickListener(v -> {
                ProgressDialog dialog = new ProgressDialog(this.getContext());
                dialog.setMessage("Loading Image");
                dialog.setCancelable(false);
                dialog.setInverseBackgroundForced(false);
                dialog.show();
                newPostViewModel.uploadAttachment(mediaName, attachmentUriToPost, task -> {
                    if (task.isSuccessful()) {
                        Post savedPost = new Post(
                                UUID.randomUUID().toString(),
                                postTitle.getText().toString(),
                                postContent.getText().toString(),
                                task.getResult().toString(),
                                newPostViewModel.getCurrUserEmail()
                        );
                        newPostViewModel.savePost(savedPost);
                        dialog.dismiss();
                        Toast.makeText(this.getContext(), "Attachment uploaded", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(this.getContext(), "Failed to upload attachment", Toast.LENGTH_SHORT).show();
                    }
                });

                Navigation.findNavController(v)
                        .navigate(AddPostFragmentDirections.actionGlobalFeedFragment());
            });
        } else {
            newPostViewModel.getPostByID(postID).observe(getViewLifecycleOwner(), post -> {
                postTitle.setText(post.getTitle());
                postContent.setText(post.getContent());
                attachmentUriToPost = Uri.parse(post.getAttachmentURI());
                saveButton.setOnClickListener(v -> {
                    Post savedPost = new Post(
                            postID,
                            postTitle.getText().toString(),
                            postContent.getText().toString(),
                            "",
                            newPostViewModel.getCurrUserEmail(),
                            post.getCreated()
                    );
                    newPostViewModel.savePost(savedPost);
                    Navigation.findNavController(v)
                            .navigate(AddPostFragmentDirections.actionGlobalFeedFragment());
                });
                Glide.with(this.getContext()).load(attachmentUriToPost).into(attachmentIV);
                deleteButton.setOnClickListener(v -> {
                    Post deletedPost = new Post(
                            postID,
                            postTitle.getText().toString(),
                            postContent.getText().toString(),
                            "",
                            newPostViewModel.getCurrUserEmail(),
                            post.getCreated()
                    );

                    deletedPost.setDeleted(true);
                    newPostViewModel.deletePost(deletedPost);
                    Navigation.findNavController(v)
                            .navigate(AddPostFragmentDirections.actionGlobalFeedFragment());
                });
            });
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            attachmentUriToPost = data.getData();
            String type = getActivity().getContentResolver().getType(attachmentUriToPost);
            mediaName = UUID.randomUUID().toString() + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(type);
            Glide.with(this.getContext()).load(attachmentUriToPost).into(attachmentIV);
        }
    }


}
