package com.example.anitailieva.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by anitailieva on 18/02/16.
 */

public class StartActivity extends AppCompatActivity {
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        Button btnStart = (Button)findViewById(R.id.btnStart);
        final EditText txtFirstPlayer = (EditText)findViewById(R.id.firstPlayer);
        final EditText txtSecondPlayer = (EditText)findViewById(R.id.secondPlayer);

        context = this;

        //Ved å klikke på Start knappen blir input sendt videre til neste activity ved hjelp av intent
        btnStart.setOnClickListener(new View.OnClickListener() {

           //Sender input(navn til spillere) til GameActivity ved å klikke på knappen start
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, GameActivity.class);
                String name1 = txtFirstPlayer.getText().toString();
                myIntent.putExtra("name1", name1);
                String name2 = txtSecondPlayer.getText().toString();
                myIntent.putExtra("name2", name2);
                startActivity(myIntent);
            }
        });
    }
}
