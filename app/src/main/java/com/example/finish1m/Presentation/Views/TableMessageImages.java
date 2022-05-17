package com.example.finish1m.Presentation.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.finish1m.R;

import java.util.ArrayList;

// таблица изображений

public class TableMessageImages extends GridLayout {

    // тип сообщения (наверное нигде не используется)
    public static final int SYSTEM_MESSAGE = 1;
    public static final int MY_MESSAGE = 2;
    public static final int NOT_MY_MESSAGE = 3;

    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private int width;
    private int type;
    private int columnCount = 2;



    public TableMessageImages(Context context) {
        super(context);
        setColumnCount(columnCount);
    }

    public TableMessageImages(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColumnCount(columnCount);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TableMessageImages);

        type = a.getInt(0, 1);
        a.recycle();


    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    // добавление изображения
    public void addImage(Bitmap b){
        this.bitmaps.add(b);
        getLayoutParams().width = 100;// width;
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width/columnCount, width/columnCount));
        imageView.setImageBitmap(b);
        addView(imageView);
    }

    // очистка
    public void removeBitmaps(){
        getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        removeAllViews();
    }


}
