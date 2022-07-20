package com.colman.social_app.fragments.feedfragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.entities.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class FeedFragment extends Fragment {

    FloatingActionButton newPostFAB;
    RecyclerView postFeed;
    PostsFeedAdapter feedAdapter;
    PostsFeedViewModel postsFeedViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText searchbar;
    SwitchCompat switchViewMyPosts;


    private SensorManager sensorManager;
    private float acceleration;
    private float accelerationCurrent;
    private float accelerationLast;
    private final float MINIMAL_ACCELERATION = 12;

    final SensorEventListener sensorListener = new SensorEventListener() {

        // calculates if device movement is bigger than a decided value, if so - enters to new post fragment
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            accelerationLast = accelerationCurrent;
            accelerationCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = accelerationCurrent - accelerationLast;
            acceleration = acceleration * 0.9f + delta;
            if (acceleration > MINIMAL_ACCELERATION) {
                Toast.makeText(getActivity(), R.string.shaking_message, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(FeedFragmentDirections.actionFeedFragmentToAddPostFragment(""));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public FeedFragment() {
        // Required empty public constructor
    }

    public interface SelectionListener {
        void clickListener(View itemView, Post post, boolean showDetails);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelFactory viewModelFactory = ((SocialApplication) getActivity().getApplication()).getViewModelFactory();
        postsFeedViewModel = new ViewModelProvider(this, viewModelFactory).get(PostsFeedViewModel.class);
//        View fragment_preview = view.findViewById(R.id.fragment_preview);
        sensorManager = (SensorManager) view.getContext().getSystemService(Context.SENSOR_SERVICE);
        acceleration = 10f;
        accelerationCurrent = SensorManager.GRAVITY_EARTH;
        accelerationLast = SensorManager.GRAVITY_EARTH;

        if (postsFeedViewModel.getEnableShaking()) {
            Objects.requireNonNull(sensorManager).registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        switchViewMyPosts = view.findViewById(R.id.switchMyPosts);
        switchViewMyPosts.setChecked(postsFeedViewModel.getViewCurrUserPosts());
        if (postsFeedViewModel.getCurrUserEmail().equals("")) {
            switchViewMyPosts.setVisibility(View.GONE);
        }
        switchPostInit(view);
        fabInit(view);

        postFeed = view.findViewById(R.id.post_feed_recyclerView);
        feedAdapter = new PostsFeedAdapter((item, post) -> ((SelectionListener) getActivity()).clickListener(item, post, post.getUploaderEmail().equals(postsFeedViewModel.getCurrUserEmail())));
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(() ->
        {
            swipeRefreshLayout.setRefreshing(false);
            postsFeedViewModel.refreshFromRemote();
        });
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (isTablet) {
            postFeed.setLayoutManager(new
                    GridLayoutManager(this.getContext(), 1));
        }else{
            postFeed.setLayoutManager(new
                    GridLayoutManager(this.getContext(), 2));
        }
        postFeed.setAdapter(feedAdapter);



        postsFeedViewModel.getAllPosts().

                observe(getViewLifecycleOwner(), posts ->

                {
                    feedAdapter.setData(posts);
                });

        initSearchBar(view);
    }

    private void initSearchBar(@NonNull View view) {
        searchbar = view.findViewById(R.id.search_bar);
        searchbar.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                postsFeedViewModel.getFilteredPosts(s.toString()).observe(getViewLifecycleOwner(), posts -> {
                    feedAdapter.setData(posts);
                });
            }
        });
    }

    private void fabInit(@NonNull View view) {
        newPostFAB = view.findViewById(R.id.newPostFAB);
        newPostFAB.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(FeedFragmentDirections.actionFeedFragmentToAddPostFragment(""));
        });
    }

    private void switchPostInit(@NonNull View view) {
        switchViewMyPosts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView toggleTV = view.findViewById(R.id.toggle_text);
                if (isChecked)
                    toggleTV.setText(R.string.toggle_text_all);
                else
                    toggleTV.setText(R.string.toggle_text_only_user);
                postsFeedViewModel.setViewCurrentUserPosts(isChecked);
                searchbar.setText("");
                postsFeedViewModel.getAllPosts().observe(getViewLifecycleOwner(), posts -> {
                    feedAdapter.setData(posts);
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onPause() {
        if (postsFeedViewModel.getEnableShaking()) {
            sensorManager.unregisterListener(sensorListener);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (postsFeedViewModel.getEnableShaking()) {
            sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        super.onResume();
    }
}