package com.example.user.skigame;

import android.graphics.Bitmap;

/**
 * Created by User on 10/17/2016.
 */

public abstract class TBImage
{
    protected Bitmap image;
    protected int full_width;
    protected int full_height;
    protected int sub_width;
    protected int sub_height;
    protected int columns;
    protected int rows;

    public TBImage(Bitmap image, int rows, int columns)
    {
        this.image = image;

        this.full_width = this.image.getWidth();
        this.full_height = this.image.getHeight();

        this.sub_width = this.full_width / columns;
        this.sub_height = this.full_height / rows;

        this.columns = columns;
        this.rows = rows;
    }

    protected Bitmap getSubImage(int row_idx, int col_idx)
    {
        return Bitmap.createBitmap(this.image,col_idx*this.sub_width,row_idx*this.sub_height,this.sub_width,this.sub_height);
    }

    protected int getColumns()
    {
        return this.columns;
    }
}
