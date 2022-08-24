package com.coding.events.bugs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressWarnings("WhitespaceAround")
public class CustomTextView extends
        TextView {


    public String text;
    public float a = 1.0f;
    public float b = 2.0f;
    public double c = 3.0;
    public CustomTextView(Context context)      {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs)                       {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void tst(){
        if (a > b) return;
        if (a > c) return;
        if (a>10) return;
if(this instanceof Object) return;
    }

    public void  test(){
        if (a > b) return;
        if (a > c) return;
        if (a>10) return;
    }

    public void  test2(){
        if (a > b) return;
        if (a > c) return;
        if (a>10) return;
    }
}
