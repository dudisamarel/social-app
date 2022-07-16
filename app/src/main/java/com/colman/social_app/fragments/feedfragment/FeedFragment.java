package com.colman.social_app.fragments.feedfragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import java.util.Objects;


public class FeedFragment extends Fragment {

    FloatingActionButton newPostFAB;
    RecyclerView postFeed;
    PostsFeedAdapter feedAdapter;
    PostsFeedViewModel postsFeedViewModel;
    SwipeRefreshLayout swipeRefreshLayout;


    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                Toast.makeText(getActivity(), "Shake event detected", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSensorManager = (SensorManager) view.getContext().getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;




        newPostFAB = view.findViewById(R.id.newPostFAB);
        newPostFAB.setOnClickListener(v-> {
            Navigation.findNavController(v).navigate(FeedFragmentDirections.actionFeedFragmentToAddPostFragment());
        });

        postFeed = view.findViewById(R.id.post_feed_recyclerView);
        feedAdapter = new PostsFeedAdapter((itemView, post) -> {
//            Navigation.findNavController(itemView).navigate(
//                    NewsFeedFragmentDirections.actionNewsFeedFragmentToArticleDetailsFragment(article.id)
//            );
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