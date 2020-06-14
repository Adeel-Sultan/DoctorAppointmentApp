package com.example.adeelsultan.phcproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adeelsultan.phcproject.Adapter.ComplaintAdapter;
import com.example.adeelsultan.phcproject.Adapter.PersonAdapter;
import com.example.adeelsultan.phcproject.ComplaintsModel.ComplaintsModel;
import com.example.adeelsultan.phcproject.DoctorsModel.DoctorsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard2Activity extends AppCompatActivity {

    ComplaintAdapter adapter;
    ArrayList<ComplaintsModel> list;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    Button signout;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        auth = FirebaseAuth.getInstance();
        signout = findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth != null){
                    auth.signOut();
                    startActivity(new Intent(Dashboard2Activity.this, LoginAsPatient.class));
                    finishAffinity();
                    finish();
                }
            }
        });

        recyclerView = findViewById(R.id.myRecyclerView2);
        manager = new LinearLayoutManager(this);
        list = new ArrayList<>();


        recyclerView.setLayoutManager(manager);
        FetchData();
    }

    private void FetchData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("ComplaintsTable")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String Name = snapshot.child("name").getValue().toString();
                            String Email = snapshot.child("email").getValue().toString();
                            String Blood = snapshot.child("blood").getValue().toString();
                            String Cnic = snapshot.child("cnic").getValue().toString();
                            String Phone = snapshot.child("phone").getValue().toString();
                            String Issue = snapshot.child("issue").getValue().toString();
                            String Gender = snapshot.child("gender").getValue().toString();
                            String Status = snapshot.child("status").getValue().toString();
                            String ImgURL = snapshot.child("imageURL").getValue().toString();



                            list.add(new ComplaintsModel(Name, Email, Blood, Cnic, Phone, Issue, Gender, Status, ImgURL));
                        }

                        adapter = new ComplaintAdapter(Dashboard2Activity.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Dashboard2Activity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
