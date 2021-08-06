package com.github.shoothzj.game2048.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.github.shoothzj.game2048.callback.GameViewCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzj on 16-2-5.
 */
public class GameView extends GridLayout {

    private final CardView[][] cardsMap = new CardView[4][4];
    private final List<Point> emptyPoints = new ArrayList<>();

    private GameViewCallback gameViewCallback = null;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public void setGameViewCallback(GameViewCallback gameViewCallback) {
        this.gameViewCallback = gameViewCallback;
    }

    private void initGameView() {
        setColumnCount(4);
        setBackgroundColor(0xFFbbada0);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else if (offsetX > 5) {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else if (offsetY > 5) {
                                swipeDown();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        addCards(getCardWidth());
        startGame();
    }

    private int getCardWidth() {
        // 屏幕信息对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();

        // 获取屏幕信息
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;

        // 1行四个卡片，每个卡片占屏幕的1/4
        return (cardWidth - 10) / 4;
    }

    private void addCards(int cardSize) {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                CardView c = new CardView(getContext());
                addView(c, cardSize, cardSize);
                cardsMap[x][y] = c;
            }
        }
    }

    private void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {
        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void swipeLeft() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int temp = x + 1; temp < 4; temp++) {
                    if (cardsMap[temp][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum());
                            cardsMap[temp][y].setNum(0);
                            x--;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[temp][y])) {
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum() * 2);
                            cardsMap[temp][y].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            doIfGameOver();
        }
    }

    private void swipeRight() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int temp = x - 1; temp >= 0; temp--) {
                    if (cardsMap[temp][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum());
                            cardsMap[temp][y].setNum(0);
                            x++;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[temp][y])) {
                            cardsMap[x][y].setNum(cardsMap[temp][y].getNum() * 2);
                            cardsMap[temp][y].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            doIfGameOver();
        }
    }

    private void swipeUp() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int temp = y + 1; temp < 4; temp++) {
                    if (cardsMap[x][temp].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum());
                            cardsMap[x][temp].setNum(0);
                            y--;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][temp])) {
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum() * 2);
                            cardsMap[x][temp].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            doIfGameOver();
        }
    }

    private void swipeDown() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int temp = y - 1; temp >= 0; temp--) {
                    if (cardsMap[x][temp].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum());
                            cardsMap[x][temp].setNum(0);
                            y++;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][temp])) {
                            cardsMap[x][y].setNum(cardsMap[x][temp].getNum() * 2);
                            cardsMap[x][temp].setNum(0);
                            addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            doIfGameOver();
        }
    }

    private void doIfGameOver() {
        boolean complete = true;
        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 || ((x > 0) && cardsMap[x][y].getNum() == cardsMap[x - 1][y].getNum()) || (x < 3 && cardsMap[x][y].getNum() == cardsMap[x + 1][y].getNum()) || (y > 0 && cardsMap[x][y].getNum() == cardsMap[x][y - 1].getNum()) || (y < 3 && cardsMap[x][y].getNum() == cardsMap[x][y + 1].getNum())) {
                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("Hi").setMessage("Game Over").setCancelable(false).setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (gameViewCallback != null) {
                        gameViewCallback.restartGame();
                    }
                    startGame();
                }
            }).show();
        }
    }

    private void addScore(int score) {
        if (gameViewCallback != null) {
            gameViewCallback.addScore(score);
        }
    }

}
