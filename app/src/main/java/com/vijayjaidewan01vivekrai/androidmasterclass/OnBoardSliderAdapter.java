package com.vijayjaidewan01vivekrai.androidmasterclass;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnBoardSliderAdapter extends PagerAdapter {

    Context context;
    private LayoutInflater inflater;
    private ArrayList list = new ArrayList();
    private DatabaseReference databaseReference;


    public OnBoardSliderAdapter(Context context) {
        this.context = context;

    }

    public int[] images = {
            R.drawable.andi,
            R.drawable.andi,
            R.drawable.andi
    };

    public String[] headings = {
            "Aestro",
            "Blender",
            "Cupcake"

    };

    public String[] descriptions = {
            "This is Android Aestro for you!",
            "This is Android Blender for you!",
            "This is Android Cuppcake for you!",

    };


    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.onboardslide_layout, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("OnBoardSlider");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView heading = view.findViewById(R.id.heading);
        TextView description = view.findViewById(R.id.description);
        ImageView imageView = view.findViewById(R.id.image_view);


        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        description.setText(descriptions[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
