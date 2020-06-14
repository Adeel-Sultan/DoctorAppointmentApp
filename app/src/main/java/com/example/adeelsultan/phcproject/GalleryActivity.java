package com.example.adeelsultan.phcproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by AdeelSultan on 4/20/2020.
 */

public class GalleryActivity  extends AppCompatActivity{

    private static final String TAG = "GalleryActivity";
    ImageView imageView;
    TextView name, specialty, address;
    String doctorname, doctorspecialty, Image, Address;
    RatingBar ratingBar;
    Button button;
    Float f_rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageView = findViewById(R.id.imageGallery);
        name = findViewById(R.id.nameGallery);
        specialty = findViewById(R.id.specialtyGallery);
        ratingBar= findViewById(R.id.rattingStar);

        doctorname = getIntent().getStringExtra("name");
        doctorspecialty = getIntent().getStringExtra("specialty");
        Address = getIntent().getStringExtra("address");
        Image = getIntent().getStringExtra("image");
        f_rating= getIntent().getFloatExtra("d_rting",0.0f);

        name.setText(doctorname);
        specialty.setText(doctorspecialty);
        ratingBar.setRating(f_rating);
        Picasso.get().load(Image).fit().into(imageView);

        button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GalleryActivity.this, ComplainActivity.class));
            }
        });



    }
}
