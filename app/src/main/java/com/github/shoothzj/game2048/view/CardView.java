package com.github.shoothzj.game2048.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by hzj on 16-2-5.
 */
public class CardView extends FrameLayout {

    private static final int[][] COLOR_ARRAY = new int[][]{
            // 2
            new int[]{Color.rgb(0.91f, 0.87f, 0.83f), Color.BLACK,},
            // 4
            new int[]{Color.rgb(0.90f, 0.86f, 0.76f), Color.BLACK,},
            // 8
            new int[]{Color.rgb(0.93f, 0.67f, 0.46f), Color.WHITE,},
            // 16
            new int[]{Color.rgb(0.94f, 0.57f, 0.38f), Color.WHITE,},
            // 32
            new int[]{Color.rgb(0.95f, 0.46f, 0.33f), Color.WHITE,},
            // 64
            new int[]{Color.rgb(0.94f, 0.35f, 0.23f), Color.WHITE,},
            // 128
            new int[]{Color.rgb(0.91f, 0.43f, 0.43f), Color.WHITE,},
            // 256
            new int[]{Color.rgb(0.91f, 0.37f, 0.37f), Color.WHITE,},
            // 512
            new int[]{Color.rgb(0.90f, 0.31f, 0.31f), Color.WHITE,},
            // 1024
            new int[]{Color.rgb(0.91f, 0.24f, 0.24f), Color.WHITE,},
            // 2048
            new int[]{Color.rgb(0.91f, 0.18f, 0.18f), Color.WHITE,},
    };

    private final TextView label;

    private int num = 0;

    public CardView(Context context) {
        super(context);
        label = new TextView(context);
        label.setTextSize(40);
        label.setBackgroundColor(0x33FFFFFF);
        label.setGravity(Gravity.CENTER);
        // -1, -1 to fill the parent
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(10, 10, 0, 0);
        addView(label, layoutParams);
        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num == 0) {
            label.setText("");
        } else {
            label.setText(String.valueOf(num));
        }
        if (num != 0) {
            int index = (int) (Math.log(num) / Math.log(2)) - 1;
            if (index >= COLOR_ARRAY.length) {
                setBackgroundColor(COLOR_ARRAY[COLOR_ARRAY.length - 1][0]);
                label.setTextColor(COLOR_ARRAY[COLOR_ARRAY.length - 1][1]);
            } else {
                setBackgroundColor(COLOR_ARRAY[index][0]);
                label.setTextColor(COLOR_ARRAY[index][1]);
            }
        } else {
            setBackgroundColor(0x33FFFFFF);
        }
    }

}
