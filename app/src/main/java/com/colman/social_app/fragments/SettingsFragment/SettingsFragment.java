package com.colman.social_app.fragments.SettingsFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.colman.social_app.R;
import com.colman.social_app.SocialApplication;
import com.colman.social_app.ViewModelFactory;
import com.colman.social_app.fragments.newPostFragment.NewPostViewModel;

public class SettingsFragment extends Fragment {

    SettingsFragmentViewModel settingsFragmentViewModel;

    Switch enableShakingSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();

        initShakingSwitch(view);
    }

    private void initShakingSwitch(@NonNull View view) {
        enableShakingSwitch = view.findViewById(R.id.enableShakingSwitch);

        enableShakingSwitch.setChecked(settingsFragmentViewModel.getEnableShaking());

        enableShakingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> settingsFragmentViewModel.setEnableShaking(isChecked));
    }

    private void initViewModel() {
        ViewModelFactory viewModelFactory = ((SocialApplication) getActivity().getApplication()).getViewModelFactory();
        settingsFragmentViewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsFragmentViewModel.class);
    }
}