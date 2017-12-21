package com.dr.app.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by valkeshpatel on 19/9/16.
 */
public class OnButtonTextChange implements View.OnTouchListener{

    Button anyView;
    Context context;
    public OnButtonTextChange(Button anyView, Activity context) {
        this.anyView = anyView;
        this.context = context;
        anyView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        if(action == MotionEvent.ACTION_DOWN) {
            anyView.setTextColor(Color.parseColor("#ffffff"));
        }else if(action == MotionEvent.ACTION_UP) {
            anyView.setTextColor(Color.parseColor("#000000"));
        }
        return false;
    }
}
