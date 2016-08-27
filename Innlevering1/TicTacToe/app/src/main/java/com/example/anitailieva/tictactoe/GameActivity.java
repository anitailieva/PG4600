package com.example.anitailieva.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Created by anitailieva on 18/02/16.
 */
public class GameActivity extends AppCompatActivity {
    private char gameBoard[][] = new char[3][3];
    private boolean firstMove = false;
    private TextView textPlayer1;
    private TextView textPlayer2;
    private TextView textViewResult;
    private final char PLAYER_ONE = 'X';
    private final char PLAYER_TWO = 'O';
    public static final int ROWS = 3, COLS = 3;
    public static final int EMPTY = 0;
    private Calendar c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);


        textPlayer1 = (TextView) findViewById(R.id.player1);
        textPlayer2 = (TextView) findViewById(R.id.player2);

        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name1");
        String name2 = intent.getStringExtra("name2");
        textPlayer1.setText(name1);
        textPlayer2.setText(name2);

        c = Calendar.getInstance();
        textViewResult = (TextView) findViewById(R.id.txtView);

        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        Button btnResults = (Button) findViewById(R.id.btnResults);


        addOnClickListeners();

        //Ved å klikke på knappen Play again blir man sendt til StartActivity der man kan starte spillet på nytt
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (v.getId() == R.id.btnPlayAgain) {
                                                    Intent myIntent = new Intent(GameActivity.this, StartActivity.class);
                                                    startActivity(myIntent);
                                                }
                                            }
                                        }

        );
        //Ved å klikke på knappen Results blir man tatt videre til ResultsActivity
        // der man ser en liste av hvem som har vunnet, dato og klokkeslett
        btnResults.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (v.getId() == R.id.btnResults) {
                                                  Intent myIntent = new Intent(GameActivity.this, ResultsActivity.class);
                                                  String result = textViewResult.getText().toString();
                                                  myIntent.putExtra("result", result);
                                                  startActivity(myIntent);
                                              }
                                          }
                                      }

        );
    }
    // Denne metoden setter knappen til å bli IKKE-klikkbare
    private void disabled() {
        TableLayout table = (TableLayout) findViewById(R.id.game_board);
        for (int i = 0; i < table.getChildCount(); i++) {
            if (table.getChildAt(i) instanceof TableRow) {
                TableRow row = (TableRow) table.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j++) {
                    if (row.getChildAt(j) instanceof Button) {
                        Button buttons = (Button) row.getChildAt(j);
                        buttons.setEnabled(false);
                    }
                }
            }
        }
    }

    //Returnere true hvis noen vinner
    private boolean winner() {
        if (win(gameBoard, 3, PLAYER_ONE)) {
            textViewResult.setText(textPlayer1.getText()+ " - " + c.getTime());
        } else if (win(gameBoard, 3, PLAYER_TWO)) {
            textViewResult.setText(textPlayer2.getText() + " - " + c.getTime());
        }else {
           return false;
        }
        return true;
    }

    //Hvis alle knappen er tatt så er det uavgjort
    private boolean draw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (gameBoard[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean win(char[][] gameBoard, int size, char player) {
        // Sjekker hver kolonne for 3 like tegn
        for (int i = 0; i < size; i++) {
            int count = 0;
            for (int j = 0; j < size; j++) {
                if (gameBoard[i][j] == player) {
                    count++;
                }
            }
            if (count >= size) {
                return true;
            }
        }

        // Sjekker hver rad for 3 like tegn
        for (int j = 0; j < size; j++) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                if (gameBoard[i][j] == player) {
                    count++;
                }
            }
            if (count >= size) {
                return true;
            }
        }

        //Sjekker diagonalt
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j && gameBoard[i][j] == player) {
                    count++;
                }
            }
        }
        if (count >= size) {
            return true;
        }

        // Sjekker diagonalt fra motsatt side enn metoden over
        count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i + j == size - 1 && gameBoard[i][j] == player) {
                    count++;
                }
            }
        }
        if (count >= size) {
            return true;
        }

        return false;
    }


    //legger til OnClickListener til alle knappene
    private void addOnClickListeners() {
        TableLayout table = (TableLayout) findViewById(R.id.game_board);
        for (int i = 0; i < table.getChildCount(); i++) {
            if (table.getChildAt(i) instanceof TableRow) {
                TableRow row = (TableRow) table.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j++) {
                    View view = row.getChildAt(j);
                    view.setOnClickListener(new ButtonsClicklistener(i, j));
                }
            }
        }
    }


    private class ButtonsClicklistener implements View.OnClickListener {

        private int a = 0;
        private int b = 0;

        public ButtonsClicklistener(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button button = (Button) view;
                gameBoard[a][b] = firstMove ? PLAYER_TWO : PLAYER_ONE;
                button.setText(firstMove ? "O" : "X");
                button.setTextColor(firstMove ? Color.parseColor("#0c0ca6") : Color.parseColor("#a80b0b"));
                button.setClickable(false);
                firstMove = !firstMove;


                //hvis true kales metoden disabled
                if (winner()) {
                    disabled();
                } else if(draw()){
                    textViewResult.setText("Draw - " + c.getTime());
                }
            }
            }
        }
    }
