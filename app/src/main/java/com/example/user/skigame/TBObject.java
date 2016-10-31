package com.example.user.skigame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by User on 10/23/2016.
 */

public abstract class TBObject extends TBImage
{
    protected int pos_x;
    protected int pos_y;

    public TBObject(Bitmap image, int rows, int columns, int init_x, int init_y)
    {
        super(image,rows,columns);
        this.pos_x = init_x;
        this.pos_y = init_y;
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);

    public abstract boolean onTouchEvent(MotionEvent event);
}
