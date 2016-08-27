package com.example.anitailieva.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by anitailieva on 18/02/16.
 */
public class ResultsActivity extends AppCompatActivity {
    String res;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);
        listView = (ListView) findViewById(R.id.listView);


        Intent intent = getIntent();
        res = intent.getStringExtra("result");
        Button btn = (Button) findViewById(R.id.btnBack);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, TempValues.tempList);

        //Listen tempList er deklarert i en annen klasse. Her legges det til elementer fra GameActivity klassen og "gis" videre til adapter
        TempValues.tempList.add(res);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Ved å klikke på Go Back blir man returnert til StartActivity igjen
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnBack) {
                    Intent myIntent = new Intent(ResultsActivity.this, StartActivity.class);
                    startActivity(myIntent);
                }
            }
        });

    }
}
