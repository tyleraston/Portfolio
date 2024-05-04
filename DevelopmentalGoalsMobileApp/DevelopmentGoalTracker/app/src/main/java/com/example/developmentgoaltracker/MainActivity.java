package com.example.developmentgoaltracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> listGroupTitles;
    HashMap<String, List<String>> listChildData;
    ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareListData();
        expandableListView = findViewById(R.id.expandableListView);
        adapter = new CustomExpandableListAdapter(this, listGroupTitles, listChildData);
        expandableListView.setAdapter(adapter);
    }

    private void prepareListData() {
        listGroupTitles = new ArrayList<>();
        listChildData = new HashMap<>();

        // Adding group data
        listGroupTitles.add("Alice");
        listGroupTitles.add("Bob");
        listGroupTitles.add("Charlie");

        // Adding child data for Alice
        List<String> alice = new ArrayList<>();
        alice.add("Detail 1 for Alice");
        alice.add("Detail 2 for Alice");
        listChildData.put(listGroupTitles.get(0), alice);

        // Adding child data for Bob
        List<String> bob = new ArrayList<>();
        bob.add("Detail 1 for Bob");
        bob.add("Detail 2 for Bob");
        listChildData.put(listGroupTitles.get(1), bob);

        // Adding child data for Charlie
        List<String> charlie = new ArrayList<>();
        charlie.add("Detail 1 for Charlie");
        charlie.add("Detail 2 for Charlie");
        listChildData.put(listGroupTitles.get(2), charlie);
    }
}