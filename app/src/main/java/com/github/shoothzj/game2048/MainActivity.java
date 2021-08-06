package com.github.shoothzj.game2048;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.shoothzj.game2048.callback.GameViewCallback;
import com.github.shoothzj.game2048.view.GameView;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private GameView gameView;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView) findViewById(R.id.tvScore);
        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setGameViewCallback(new GameViewCallback() {
            @Override
            public void addScore(int num) {
                MainActivity.this.addScore(num);
            }

            @Override
            public void restartGame() {
                MainActivity.this.clearScore();
            }
        });
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(String.valueOf(score));
    }

    public void addScore(int add) {
        score += add;
        showScore();
    }

}