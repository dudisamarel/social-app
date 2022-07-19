package com.colman.social_app.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.colman.social_app.R;

public class IntroFragment extends Fragment {
//
    private static final String TAG = IntroFragment.class.getName();
    private Data data;
    private int position;

    public static IntroFragment newInstance(Data data, int position) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putSerializable("data", data);
        b.putInt("position", position);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = (Data) getArguments().getSerializable("data");
        position = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_intro, container, false);
        view.setTag(position);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageView tv = view.findViewById(R.id.tv);
        tv.setImageResource(data.getImg());

        TextView description = view.findViewById(R.id.description);
        description.setText(data.getText());
    }

}