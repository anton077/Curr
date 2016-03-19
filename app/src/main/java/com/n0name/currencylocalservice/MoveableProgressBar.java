package com.n0name.currencylocalservice;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by N0Name on 13-Feb-16.
 */
public class MoveableProgressBar extends ProgressBar
{
    public interface OnProgressBarChangeListener
    {
        void onProgressChanged(View v, int progress);
    }
    private OnProgressBarChangeListener listener;


    public MoveableProgressBar(Context context)
    {
        super(context,null,android.R.attr.progressBarStyleHorizontal);
    }
    public void setOnProgressChangeListener(OnProgressBarChangeListener lstnr)
    {
        this.listener = lstnr;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE)
        {
            int progress = 0;
            double x = event.getX();
            double width = getWidth();
            progress = (int) Math.round((double) getMax() * (x / width));
            if (progress < 0)
                progress = 0;
            this.setProgress(progress);
            if (listener != null)
                listener.onProgressChanged(this, progress);
        }
        return true;
    }
}