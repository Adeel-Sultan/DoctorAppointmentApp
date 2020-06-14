package com.example.adeelsultan.phcproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.adeelsultan.phcproject.Adapter.PersonAdapter;
import com.example.adeelsultan.phcproject.DoctorsModel.DoctorsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FragmentTwo extends Fragment {

//    PersonAdapter adapter;
//    ArrayList<DoctorsModel> list;
//    RecyclerView recyclerView;
//    LinearLayoutManager manager;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view =  inflater.inflate(R.layout.fragment_fragment_two, container, false);
//
//        // paste code here
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
//        manager = new LinearLayoutManager(getActivity());
//        list = new ArrayList<>();
//
//        recyclerView.setLayoutManager(manager);
//        FetchData();
//
//        return view;
//    }
//
//    private void FetchData() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        reference.child("DoctorTable")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                            String Name = snapshot.child("name").getValue().toString();
//                            String Email = snapshot.child("email").getValue().toString();
//                            String Password = snapshot.child("password").getValue().toString();
//                            String Phone = snapshot.child("phone").getValue().toString();
//                            String Address = snapshot.child("address").getValue().toString();
//                            String Specialty = snapshot.child("specialty").getValue().toString();
//                            String Rating=snapshot.child("d_rting").getValue().toString();
//                            String Gender = snapshot.child("gender").getValue().toString();
//                            String ImgURL = snapshot.child("imageURL").getValue().toString();
//
//
//                            list.add(new DoctorsModel(Name, Email, Password, Phone, Address, Specialty,Rating, Gender, ImgURL));
//                        }
//
//                        adapter = new PersonAdapter(getActivity(), list);
//                        recyclerView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(getActivity(), databaseError.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    PersonAdapter adapter;
    ArrayList<DoctorsModel> list;
    RecyclerView recyclerView;
    LinearLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_two, container, false);

        // paste code here

        recyclerView = (RecyclerView) view.findViewById(R.id.myRecyclerView);
        manager = new LinearLayoutManager(getActivity());
        list = new ArrayList<>();
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        recyclerView.setLayoutManager(manager);


        Query query1;
        query1 = FirebaseDatabase.getInstance().getReference("DoctorTable")
                .orderByChild("d_rting");
        query1.addListenerForSingleValueEvent(valueEventListener);


        return view;
    }

       ValueEventListener valueEventListener= new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String Name = snapshot.child("name").getValue().toString();
                            String Email = snapshot.child("email").getValue().toString();
                            String Password = snapshot.child("password").getValue().toString();
                            String Phone = snapshot.child("phone").getValue().toString();
                            String Address = snapshot.child("address").getValue().toString();
                            String Specialty = snapshot.child("specialty").getValue().toString();
                            String Rating=snapshot.child("d_rting").getValue().toString();
                            String Gender = snapshot.child("gender").getValue().toString();
                            String ImgURL = snapshot.child("imageURL").getValue().toString();


                            list.add(new DoctorsModel(Name, Email, Password, Phone, Address, Specialty,Rating, Gender, ImgURL));
                        }

                        adapter = new PersonAdapter(getActivity(), list);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                };

}
