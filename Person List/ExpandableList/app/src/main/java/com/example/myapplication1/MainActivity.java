package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandableListView);

        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Add header data
        listDataHeader.add("Person1");
        listDataHeader.add("Person2");
        listDataHeader.add("Person3");
        listDataHeader.add("Person4");
        listDataHeader.add("Person5");

        // Add child data
        List<String> group1 = new ArrayList<>();
        group1.add("Goal 1");
        group1.add("Goal 2");
        group1.add("Goal 3");
        for (int i = 0; i < listDataHeader.size(); i++) {
            listDataChild.put(listDataHeader.get(i), group1);
        }
    }
}
