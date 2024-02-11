package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// File reading
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        prepareListData();

        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

    }
    public List<String> readFromFile(Context context) {
        List<String> lines = new ArrayList<>();
        try {
            // Accessing a text file placed in the raw folder as I'm told is the common
            //   method for text files.
            InputStream inputStream = getResources().openRawResource(R.raw.names);

            // Use getAssets is file is in assets folder.
            //InputStream inputStream = context.getAssets().open(fileName);

            // Read it using BufferedReader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            // Read every line and add it to the list
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            // Close the streams
            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        List<String> lines = readFromFile(this);
        // Add header data
        listDataHeader.addAll(lines);


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
