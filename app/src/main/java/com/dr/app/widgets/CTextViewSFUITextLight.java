package com.dr.app.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dr.app.R;


public class CTextViewSFUITextLight extends TextView {

	public CTextViewSFUITextLight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode())
		init(attrs);
	}

	public CTextViewSFUITextLight(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
		init(attrs);

	}

	public CTextViewSFUITextLight(Context context) {
		super(context);
		if (!isInEditMode())
		init(null);
	}
	
	private void init(AttributeSet attrs) {
		
		if (attrs!=null) {
			 TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
			 String fontName = a.getString(R.styleable.MyTextView_fontName);
			 if (fontName!=null) {
				 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
				 setTypeface(myTypeface);
			 }
			 else
			 {
				 Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
				 setTypeface(myTypeface);
			 }
			 a.recycle();
		}
	}

}