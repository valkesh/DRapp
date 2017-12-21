package com.dr.app.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.dr.app.R;

/**
 * Created by valkesh.patel on 5/5/16.
 */
public class LoaderDarkView extends LinearLayout {
    public LoaderDarkView(Context context) {
        super(context);
        init();
    }

    public LoaderDarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_dark_view, this);
        RotateAnimation rotateanim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateanim.setDuration(1200);
        rotateanim.setRepeatCount(1000);
        rotateanim.setInterpolator(new LinearInterpolator());
        findViewById(R.id.loader1).startAnimation(rotateanim);
    }
}
