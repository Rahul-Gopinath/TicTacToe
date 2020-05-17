package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private TextView player1Points;
    private TextView player2Points;

    private Button resetButton;

    private int roundCount = 0;
    private Boolean player1Turn = true;
    private int p1Points = 0;
    private int p2Points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Points = findViewById(R.id.text_view_player1);
        player2Points = findViewById(R.id.text_view_player2);
        resetButton = findViewById(R.id.button_reset);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String id = "button" + i + j;
                int resID = getResources().getIdentifier(id, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1Points = 0;
                p2Points = 0;
                player1Points.setText("Player1 : 0");
                player2Points.setText("Player2 : 0");
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")) {
            return;
        }
        else {
            if (player1Turn) {
                ((Button) v).setText("X");
            } else {
                ((Button) v).setText("O");
            }
        }
        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            }
            else {
                player2Wins();
            }
        }
        else if (roundCount == 9) {
            draw();
        }
        else {
            player1Turn = !player1Turn;
        }
    }

    private Boolean checkForWin() {
        String[][] fields = new String[3][3];
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++) {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals("")) {
                return true;
            }
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals("")) {
                return true;
            }
        }
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals("")) {
            return true;
        }
        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0]) && !fields[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        p1Points++;
        player1Points.setText("Player1 : " + p1Points);
        Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void player2Wins() {
        p2Points++;
        player2Points.setText("Player2 : " + p2Points);
        Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        roundCount = 0;
        player1Turn = true;
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                buttons[i][j].setText("");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("player1Points", p1Points);
        outState.putInt("player2Points", p2Points);
        outState.putInt("roundCount", roundCount);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);

        p1Points = savedInstanceState.getInt("player1Points");
        p2Points = savedInstanceState.getInt("player2Points");
        roundCount = savedInstanceState.getInt("roundCount");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }
}
