package com.example.mylibrary.src;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.mylibrary.R;

public class MyView extends LinearLayout {

    public MyView(Context context) {
        super(context);
        initialize(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context){
        inflate(context, R.layout.my_view, this);
    }

}