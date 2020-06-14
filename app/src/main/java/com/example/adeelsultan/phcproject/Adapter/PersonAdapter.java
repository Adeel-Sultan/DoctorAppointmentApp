package com.example.adeelsultan.phcproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adeelsultan.phcproject.DoctorsModel.DoctorsModel;
import com.example.adeelsultan.phcproject.GalleryActivity;
import com.example.adeelsultan.phcproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AdeelSultan on 4/18/2020.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder>{
    private Context ctx;
    private ArrayList<DoctorsModel> list;

    public PersonAdapter(Context ctx, ArrayList<DoctorsModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.user_display_layout, parent,false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        final DoctorsModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.specialty.setText(model.getSpecialty());
        holder.d_rating_star.setRating(model.getD_rting());
    //     holder.address.setText(model.getAddress());


        String url = model.getImageURL();
        Picasso.get().load(url).fit().into(holder.img);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, GalleryActivity.class);
                intent.putExtra("image", model.getImageURL());
                intent.putExtra("name", model.getName());
                intent.putExtra("specialty", model.getSpecialty());
                intent.putExtra("d_rting ",model.getD_rting());
//                intent.putExtra("address", model.getAddress());
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder{

        TextView name, specialty, address;
        ImageView img;
        LinearLayout layout;
        Button button;
        RatingBar d_rating_star;

        public PersonViewHolder(View itemView) {
            super(itemView);



            name = itemView.findViewById(R.id.user_profile_name);
            specialty = itemView.findViewById(R.id.user_profile_specialty);
//            address = itemView.findViewById(R.id.user_profile_address);
            img = itemView.findViewById(R.id.user_profile_image);
            button = itemView.findViewById(R.id.button);
            d_rating_star= itemView.findViewById(R.id.rattingStr);

        }
    }

}
