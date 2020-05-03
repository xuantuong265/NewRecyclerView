package com.example.newrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference mData;
    ArrayList<Students> studentsArrayList;
    StudentsAdapter studentsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        studentsArrayList = new ArrayList<>();

        mData = FirebaseDatabase.getInstance().getReference().child("Students");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        studentsAdapter = new StudentsAdapter(studentsArrayList, getApplication());
        recyclerView.setAdapter(studentsAdapter);

        loadData();


    }


    private  void loadData(){
       mData.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Students std = dataSnapshot.getValue(Students.class);
               studentsArrayList.add(new Students(std.getEdtfistName().toString(), std.getEdtLastName().toString(), std.getRadioButton().toString(),
                       std.getEdtChoseBirthday().toString(), std.getEdtPhoneNumber().toString(), std.getEdtAddress().toString(), std.getLinkAvt().toString(), std.isPermission()));
               studentsAdapter.notifyDataSetChanged();

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

}
