package com.example.adeelsultan.phcproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adeelsultan.phcproject.ComplaintToPatientActivity;
import com.example.adeelsultan.phcproject.ComplaintsModel.ComplaintsModel;
import com.example.adeelsultan.phcproject.GalleryActivity;
import com.example.adeelsultan.phcproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>{
    private Context ctx;
    private ArrayList<ComplaintsModel> list;

    public ComplaintAdapter(Context ctx, ArrayList<ComplaintsModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.complaints_display_layout, parent, false);
        return new ComplaintViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        ComplaintsModel model = list.get(position);

        holder.name.setText(model.getName());
        holder.blood.setText(model.getBlood());
        holder.issue.setText(model.getIssue());

        String url = model.getImageURL();
        Picasso.get().load(url).fit().into(holder.img);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ComplaintToPatientActivity.class);
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder{

        TextView name, blood, issue;
        ImageView img;
        Button button;

        public ComplaintViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.complain_profile_name);
            blood = itemView.findViewById(R.id.complain_profile_blood);
            issue = itemView.findViewById(R.id.complain_profile_issue);
            img = itemView.findViewById(R.id.complain_profile_image);
            button = itemView.findViewById(R.id.read_complain_button);
        }
    }
}
