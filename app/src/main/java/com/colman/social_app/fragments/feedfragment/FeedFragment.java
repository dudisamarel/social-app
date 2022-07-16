package com.colman.social_app.fragments.feedfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.fragments.newPostFragment.NewPostViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class FeedFragment extends Fragment {

    FloatingActionButton newPostFAB;
    RecyclerView postFeed;
    PostsFeedAdapter feedAdapter;
    PostsFeedViewModel postsFeedViewModel;
    SwipeRefreshLayout swipeRefreshLayout;


    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newPostFAB = view.findViewById(R.id.newPostFAB);
        newPostFAB.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(FeedFragmentDirections.actionFeedFragmentToAddPostFragment(""));
        });

        postFeed = view.findViewById(R.id.post_feed_recyclerView);
        feedAdapter = new PostsFeedAdapter((itemView, post) -> {

            // if clicked post belong to curr user
            if (post.getUploaderEmail().equals(postsFeedViewModel.getCurrUserEmail())) {
                Navigation.findNavController(itemView).navigate(
                        FeedFragmentDirections.actionFeedFragmentToAddPostFragment(post.getId())
                );
            } else { // if clicked post belong to other user
                Navigation.findNavController(itemView).navigate(
                        FeedFragmentDirections.actionFeedFragmentToPostDetailsFragment(post.getId())
                );
            }
            Log.i("POST_CLICK", post.getTitle());
        });

        ViewModelFactory viewModelFactory = ((SocialApplication) getActivity().getApplication()).getViewModelFactory();
        postsFeedViewModel = new ViewModelProvider(this, viewModelFactory).get(PostsFeedViewModel.class);


        // TODO: INITIATE THE REFRESH FUNCTION
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            postsFeedViewModel.refreshFromRemote();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        postFeed.setLayoutManager(linearLayoutManager);
        postFeed.setAdapter(feedAdapter);

        postsFeedViewModel.getAllPosts().observe(getViewLifecycleOwner(), posts -> {
            feedAdapter.setData(posts);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
}