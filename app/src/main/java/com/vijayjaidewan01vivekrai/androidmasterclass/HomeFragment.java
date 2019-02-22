package com.vijayjaidewan01vivekrai.androidmasterclass;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener {

    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialAd2;
    private InterstitialAd interstitialAd3;

    private RecyclerView recyclerView;
    private HomeAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploads;
    private ValueEventListener valueEventListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        interstitialAd = new InterstitialAd(getContext());
        interstitialAd2 = new InterstitialAd(getContext());
        interstitialAd3 = new InterstitialAd(getContext());

        interstitialAd.setAdUnitId("ca-app-pub-7875006650320994/4991558411");
        interstitialAd2.setAdUnitId("ca-app-pub-7875006650320994/9940632769");
        interstitialAd3.setAdUnitId("ca-app-pub-7875006650320994/1678999363");

        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd2.loadAd(request);
        interstitialAd3.loadAd(request);

        progressBar = view.findViewById(R.id.progressBar);

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeColors(Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });

        recyclerView = view.findViewById(R.id.rvNumbers);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL));

        uploads = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Home");
        databaseReference.keepSynced(true);

        imageAdapter = new HomeAdapter(getActivity(), uploads);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(this);

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setMkey(postSnapshot.getKey());
                    uploads.add(upload);

                }
                imageAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Unable to Retrieve , some error occured!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onItemClick(int position) {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }

        Upload selectedItem = uploads.get(position);
        String videoId = selectedItem.getVideoId();
        Intent intent = new Intent(getContext(), VideoExoActivity.class);
        intent.putExtra("videoId", videoId);
        startActivity(intent);

    }

    @Override
    public void onWhatEverClick(int position) {
        if (interstitialAd2.isLoaded()) {
            interstitialAd2.show();
        }



    }

    @Override
    public void onDeleteClick(int position) {

        if (interstitialAd3.isLoaded()) {
            interstitialAd3.show();
        }


    }

}