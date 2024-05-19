package com.example.developmentgoaltracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String> listGroupTitles;
    HashMap<String, List<String>> listChildData;
    CustomExpandableListAdapter adapter;
    DatabaseReference database;
    private DatabaseReference clientsRef;
    FloatingActionButton fabAddClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding new clients
        fabAddClient = findViewById(R.id.fab_add_client);
        fabAddClient.setOnClickListener(v -> {
            // Trigger the action for adding a new client
            showAddClientDialog();
        });

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().getReference();

        // Assuming Firebase is already initialized elsewhere
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        clientsRef = database.getReference("clients");

        // Initialize lists
        listGroupTitles = new ArrayList<>();
        listChildData = new HashMap<>();

        prepareListData();
        expandableListView = findViewById(R.id.expandableListView);
        adapter = new CustomExpandableListAdapter(this, listGroupTitles, listChildData);
        expandableListView.setAdapter(adapter);

        loadUsers();  // This should be called to load data
        listenForClientUpdates();
    }

    private void prepareListData() {
        database.child("expandableList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listGroupTitles = new ArrayList<>();
                listChildData = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String group = snapshot.getKey();
                    listGroupTitles.add(group);
                    List<String> childItems = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.child("items").getChildren()) {
                        childItems.add(childSnapshot.getValue(String.class));
                    }
                    listChildData.put(group, childItems);
                }
                adapter.updateData(listGroupTitles, listChildData); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadExpandableListData:onCancelled", databaseError.toException());
            }
        });
    }

    private void loadUsers() {
        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listGroupTitles.clear();
                listChildData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userName = snapshot.getValue(String.class);
                    listGroupTitles.add(userName);
                    // Assuming you want to add dummy children data or handle children differently
                    List<String> children = new ArrayList<>();
                    children.add("Detail for " + userName);
                    listChildData.put(userName, children);
                }
                adapter.updateData(listGroupTitles, listChildData);  // Update the adapter
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "loadUsers:onCancelled", databaseError.toException());
            }
        });
    }
    private void listenForClientUpdates() {
        clientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> newGroupTitles = new ArrayList<>();
                HashMap<String, List<String>> newChildData = new HashMap<>();

                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    String clientName = clientSnapshot.child("name").getValue(String.class);
                    List<String> goals = new ArrayList<>();
                    for (DataSnapshot goalSnapshot : clientSnapshot.child("goals").getChildren()) {
                        String goal = goalSnapshot.getValue(String.class);
                        goals.add(goal);
                    }
                    newGroupTitles.add(clientName);
                    newChildData.put(clientName, goals);
                }

                adapter.updateData(newGroupTitles, newChildData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MainActivity", "loadClients:onCancelled", databaseError.toException());
            }
        });
    }
    private void showAddClientDialog() {
        // Create an AlertDialog with an EditText for entering the client's name
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Client's Name and Goals")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String clientName = input.getText().toString().trim();
                    // Example static goals
                    List<String> goals = Arrays.asList("Goal 1", "Goal 2");
                    addNewClient(clientName, goals);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    public void addNewClient(String clientName, List<String> goals) {
        HashMap<String, Object> clientData = new HashMap<>();
        clientData.put("name", clientName);
        clientData.put("goals", goals);

        // Push the new client to the Firebase database
        clientsRef.push().setValue(clientData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Client added successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to add client.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
