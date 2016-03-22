package ru.mishaignatov.touristquiz.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/***
 * Created by Leva on 19.03.2016.
 */
public class CircleButton extends ImageView {

    public CircleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleButton(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(height,height);
    }
}
