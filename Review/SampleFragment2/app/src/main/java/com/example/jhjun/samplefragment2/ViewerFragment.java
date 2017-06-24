package com.example.jhjun.samplefragment2;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by jhjun on 2017-06-23.
 */

public class ViewerFragment extends Fragment {
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewer,container,false);

        imageView = (ImageView) view.findViewById(R.id.imageView);

        return view;
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }
}
