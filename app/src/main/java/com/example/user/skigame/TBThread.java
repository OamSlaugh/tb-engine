package com.example.user.skigame;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by User on 10/23/2016.
 */

public class TBThread extends Thread
{
    TBSurface mainSurface;
    SurfaceHolder surfaceHolder;
    boolean isRunning;
    private final long CYCLE_TIME_MS = 10;

    public TBThread(TBSurface surface)
    {
        this.mainSurface = surface;
    }

    public void run()
    {
        while(isRunning)
        {
            Canvas canvas = null;
            long start = System.currentTimeMillis();
            long timer;

            try
            {
                if (mainSurface.isCreated())
                {
                    canvas = mainSurface.getSurfaceHolder().lockCanvas();

                    synchronized (canvas)
                    {
                        mainSurface.update();
                        mainSurface.draw(canvas);
                    }
                }
            }
            catch(Exception e)
            {
                //Oops
            }
            finally
            {
                if(canvas != null)
                {
                    //Make sure we try to clean up regardless
                    mainSurface.getSurfaceHolder().unlockCanvasAndPost(canvas);
                }
            }

            timer = (System.currentTimeMillis() - start);
            if(timer < CYCLE_TIME_MS)
            {
                try
                {
                    this.sleep(CYCLE_TIME_MS - timer);
                }
                catch (InterruptedException e)
                {

                }
            }
        }
    }

    public void startThread()
    {
        this.isRunning = true;
    }

    public void stopThread()
    {
        this.isRunning = false;
    }
}
