package com.dr.app.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.dr.app.R;


public class LoadingView extends LinearLayout {

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.image_pager_item, this);
        RotateAnimation rotateanim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateanim.setDuration(1200);
        rotateanim.setRepeatCount(1000);
        rotateanim.setInterpolator(new LinearInterpolator());
        findViewById(R.id.loader1).startAnimation(rotateanim);
    }
}
