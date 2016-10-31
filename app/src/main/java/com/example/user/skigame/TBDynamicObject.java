package com.example.user.skigame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by User on 10/17/2016.
 */

public class TBDynamicObject extends TBObject
{
    private final int STATE_IDLE = 0;
    private final int DIR_UP = 1;
    private final int DIR_DOWN = 2;
    private final int DIR_LEFT = 3;
    private final int DIR_RIGHT = 4;

    boolean single_image;
    Bitmap default_image;
    ArrayList<Bitmap> up_images;
    ArrayList<Bitmap> down_images;
    ArrayList<Bitmap> left_images;
    ArrayList<Bitmap> right_images;
    int up_speed;
    int down_speed;
    int left_speed;
    int right_speed;
    float vx,vy;
    int obj_state, last_state;
    int image_idx;
    int mass;
    long last_timestamp;
    long last_dt;
    long distance;

    public TBDynamicObject(Bitmap image, int x, int y, int rows, int columns)
    {
        super(image,rows,columns,x,y);
        if(rows != 1 || columns != 1)
            single_image = false;
        else
            single_image = true;

        last_timestamp = 0;
        last_dt = 0;
        distance = 0;

        up_images = new ArrayList<Bitmap>(1);
        down_images = new ArrayList<Bitmap>(1);
        left_images = new ArrayList<Bitmap>(1);
        right_images = new ArrayList<Bitmap>(1);

        this.vx = 0;
        this.vy = 0;
    }

    public TBDynamicObject(Bitmap image, int x, int y)
    {
        this(image,x,y,1,1);
        single_image = true;
        this.default_image = image;
    }

    public void setMass(int mass)
    {
        this.mass = mass;
    }

    public void setVelocity(float vx, float vy)
    {
        this.vx = vx;
        this.vy = vy;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        boolean retval = false;

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                this.pos_x = (int)event.getX();
                this.pos_y = (int)event.getY();
                retval = true;
                break;
            default:
                retval = false;
                break;
        }

        return retval;
    }

    public int getMass()
    {
        return this.mass;
    }

    public void setSpeed(int up, int down, int left, int right)
    {
        this.up_speed = up;
        this.down_speed = down;
        this.left_speed = left;
        this.right_speed = right;
    }

    public void update()
    {
        long timestamp, dt, dt_steps;
        int speed;

        timestamp = System.currentTimeMillis();

        if(last_timestamp == 0)
        {
            dt = 0;
        }
        else
        {
            dt = timestamp - last_timestamp;
        }

        /*last_dt += dt;

        if(last_dt >= DT_THRESHOLD_MS)
        {
            dt_steps = last_dt / DT_THRESHOLD_MS;
            dt = dt_steps * DT_THRESHOLD_MS;
            last_dt -= dt;
        }*/

        if(Math.abs(this.vx) > Math.abs(this.vy))
        {
            if(this.vx > 0)
            {
                obj_state = DIR_RIGHT;
                speed = this.right_speed;
            }
            else
            {
                obj_state = DIR_LEFT;
                speed = this.left_speed;
            }
        }
        else
        {
            if(this.vy > 0)
            {
                obj_state = DIR_UP;
                speed = this.up_speed;
            }
            else
            {
                obj_state = DIR_DOWN;
                speed = this.down_speed;
            }
        }

        if(obj_state != last_state)
        {
            image_idx = 0;
            distance = 0;
        }

        this.pos_x += this.vx*dt;
        this.pos_y += this.vy*dt;

        distance += (this.vx*dt)*(this.vx*dt) + (this.vy*dt)*(this.vy*dt);

        if(distance >= speed*speed)
        {
            image_idx++;
            distance = 0;
        }

        last_timestamp = timestamp;
        last_state = obj_state;
    }

    public void draw(Canvas canvas)
    {
        Bitmap current;

        try {
            switch (obj_state) {
                case DIR_UP:
                    current = up_images.get(image_idx);
                    break;
                case DIR_DOWN:
                    current = down_images.get(image_idx);
                    break;
                case DIR_LEFT:
                    current = left_images.get(image_idx);
                    break;
                case DIR_RIGHT:
                    current = right_images.get(image_idx);
                    break;
                case STATE_IDLE:
                    current = default_image;
                    break;
                default:
                    current = default_image;
                    break;
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            current = default_image;
        }
        current = default_image;

        canvas.drawBitmap(current,this.pos_x,this.pos_y,null);
    }

    public void setUpMotion(int startRow, int startCol, int endRow, int endCol)
    {
        int count,row_idx,col_idx;
        count = (endRow - startRow)*this.columns - startCol + endCol + 1;
        up_images.clear();
        row_idx = startRow;
        col_idx = startCol;
        for(int i = 0; i < count; i++)
        {
            up_images.add(getSubImage(row_idx,col_idx));
            col_idx++;
            if(col_idx >= this.columns)
            {
                col_idx = 0;
                row_idx++;
            }
        }
    }

    public void setDownMotion(int startRow, int startCol, int endRow, int endCol)
    {
        int count,row_idx,col_idx;
        count = (endRow - startRow)*this.columns - startCol + endCol + 1;
        down_images.clear();
        row_idx = startRow;
        col_idx = startCol;
        for(int i = 0; i < count; i++)
        {
            down_images.add(getSubImage(row_idx,col_idx));
            col_idx++;
            if(col_idx >= this.columns)
            {
                col_idx = 0;
                row_idx++;
            }
        }
    }

    public void setLeftMotion(int startRow, int startCol, int endRow, int endCol)
    {
        int count,row_idx,col_idx;
        count = (endRow - startRow)*this.columns - startCol + endCol + 1;
        left_images.clear();
        row_idx = startRow;
        col_idx = startCol;
        for(int i = 0; i < count; i++)
        {
            left_images.add(getSubImage(row_idx,col_idx));
            col_idx++;
            if(col_idx >= this.columns)
            {
                col_idx = 0;
                row_idx++;
            }
        }
    }

    public void setRightMotion(int startRow, int startCol, int endRow, int endCol)
    {
        int count,row_idx,col_idx;
        count = (endRow - startRow)*this.columns - startCol + endCol + 1;
        right_images.clear();
        row_idx = startRow;
        col_idx = startCol;
        for(int i = 0; i < count; i++)
        {
            right_images.add(getSubImage(row_idx,col_idx));
            col_idx++;
            if(col_idx >= this.columns)
            {
                col_idx = 0;
                row_idx++;
            }
        }
    }
}
