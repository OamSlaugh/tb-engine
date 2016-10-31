package com.example.user.skigame;


import android.content.Context;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

import java.util.LinkedList;

/**
 * Created by User on 10/17/2016.
 */

public class TBSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private LinkedList<TBObject> objects;
    boolean created;
    SurfaceHolder surfaceHolder;
    TBThread surfaceThread;

    public TBSurface(Context context)
    {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
        objects = new LinkedList<TBObject>();
    }

    public void addObject(TBDynamicObject newObject)
    {
        objects.add(newObject);
    }

    public void update()
    {
        for(int i = 0; i < objects.size(); i++)
        {
            objects.get(i).update();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        for(int i = 0; i < objects.size(); i++)
        {
            objects.get(i).draw(canvas);
        }
    }

    public boolean isCreated()
    {
        return this.created;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean retval = false;

        /*switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                retval = true;
                break;
            default:
                retval = false;
                break;
        }*/

        for(int i = 0; i < objects.size(); i++)
        {
            objects.get(i).onTouchEvent(event);
        }

        return true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        this.created = false;
        //this.surfaceThread.stopThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        this.created = true;
        this.surfaceHolder = holder;
        /*this.surfaceThread = new TBThread(this);
        this.surfaceThread.startThread();
        this.surfaceThread.start();*/
    }

    public SurfaceHolder getSurfaceHolder()
    {
        return this.surfaceHolder;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }
}
