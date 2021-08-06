package com.github.shoothzj.game2048.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by hzj on 16-2-5.
 */
public class CardView extends FrameLayout {

    private final TextView label;

    private int num = 0;

    public CardView(Context context) {
        super(context);
        label = new TextView(context);
        label.setTextSize(40);
        label.setBackgroundColor(0x33ffffff);
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
    }

    public boolean equals(CardView o) {
        return getNum() == o.getNum();
    }
}
