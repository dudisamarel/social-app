package com.colman.social_app.fragments.newPostFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.entities.Post;

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

    private EditText postTitle;
    private EditText postContent;

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

        // new post - no getting post details is needed
        if (postID.equals("")) {
            deleteButton.setVisibility(View.GONE);

            saveButton.setOnClickListener(v -> {
                Post savedPost = new Post(
                        UUID.randomUUID().toString(),
                        postTitle.getText().toString(),
                        postContent.getText().toString(),
                        "",
                        newPostViewModel.getCurrUserEmail()
                );
                newPostViewModel.savePost(savedPost);
                Navigation.findNavController(v)
                        .navigate(AddPostFragmentDirections.actionGlobalFeedFragment());
            });
        } else {
            newPostViewModel.getPostByID(postID).observe(getViewLifecycleOwner(), post -> {
                postTitle.setText(post.getTitle());
                postContent.setText(post.getContent());

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
}