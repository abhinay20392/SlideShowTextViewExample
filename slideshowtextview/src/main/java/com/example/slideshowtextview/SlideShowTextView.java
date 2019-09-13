package com.example.slideshowtextview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

@SuppressLint("AppCompatCustomView")
public class SlideShowTextView extends TextView {
    private Thread thread;
    private Random rand;
    private ArrayList<String> quotation;

    public SlideShowTextView(Context context) {
        super(context);
    }

    public SlideShowTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SlideShowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, @Nullable AttributeSet attrs) {
        rand = new Random();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SlideShowTextView);

        final int intarval = a.getInteger(R.styleable.SlideShowTextView_interval, 4000);
        CharSequence[] source = a.getTextArray(R.styleable.SlideShowTextView_source);
        quotation = new ArrayList<>();

        for (int i = 0; i < source.length; i++) {
            quotation.add(source[i].toString());
        }
        thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(intarval);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setText(getQuotation(quotation));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }


    public String getQuotation(ArrayList<String> quotation) {
        int randomNum = rand.nextInt(quotation.size());
        return quotation.get(randomNum);
    }

}
