package com.example.user.skigame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by User on 10/17/2016.
 */

public class TBGame
{
    TBSurface mainSurface;
    TBThread gameThread;
    TBDynamicObject testObject;
    Bitmap icon;

    public TBGame(Context context)
    {
        mainSurface = new TBSurface(context);
        icon = BitmapFactory.decodeResource(mainSurface.getResources(),R.drawable.star);
        testObject = new TBDynamicObject(icon,100,100);
        testObject.setVelocity(0.0f,0.0f);
        mainSurface.addObject(testObject);
        gameThread = new TBThread(mainSurface);
        gameThread.startThread();
        gameThread.start();
    }

    public TBSurface getSurface()
    {
        return mainSurface;
    }
}
