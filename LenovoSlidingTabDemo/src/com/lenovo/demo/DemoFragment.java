package com.lenovo.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.internal.R;

public class DemoFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static DemoFragment newInstance(int position) {
        DemoFragment f = new DemoFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.slidingtab_page_sample, container, false);

        TextView sample = (TextView) rootView.findViewById(R.id.slidingtab_sample_text);
        sample.setText("DEMO---PAGE---"+position);

        return rootView;
    }
}