package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

public class CheeseDetailFragment extends Fragment {

    CoordinatorLayout mCoordinatorLayout;
    ImageView mImageView;
    Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingToolbar;
    AppBarLayout mAppBarLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.main_content);
        mImageView = (ImageView) view.findViewById(R.id.backdrop);

        mCollapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setTitle("nanana " + new Random().nextInt());

        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ViewCompat.requestApplyInsets(mCoordinatorLayout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(mImageView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.sample_actions, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
