package com.github.shoothzj.game2048;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.shoothzj.game2048.callback.GameViewCallback;
import com.github.shoothzj.game2048.view.GameView;
import com.github.shoothzj.game2048.vm.GameViewModel;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameViewModel model = new ViewModelProvider(this).get(GameViewModel.class);
        tvScore = (TextView) findViewById(R.id.tvScore);
        gameView = (GameView) findViewById(R.id.gameView);
        model.getScore().observe(this, integer -> tvScore.setText(String.valueOf(integer)));
        gameView.setGameViewCallback(new GameViewCallback() {
            @Override
            public void addScore(int num) {
                model.addScore(num);
            }

            @Override
            public void restartGame() {
                model.setScore(0);
            }
        });
    }
}