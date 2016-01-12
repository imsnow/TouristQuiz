package ru.mishaignatov.touristquiz.ui;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by Leva on 09.01.2016.
 * it is child of TextView
 * it has auto scalable text
 */
public class AnswerButton extends TextView {

    private static final float MIN_SIZE = 10f;
    private static final float THRESHOLD = 0.5f;

    private TextPaint mTextPaint;

    private float mStartTextSize;
    private float mPreferTextSize;

    public AnswerButton(Context context) {
        super(context);
        init();
    }

    public AnswerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnswerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mTextPaint = new TextPaint();
        mStartTextSize = getTextSize();
        mPreferTextSize = getTextSize();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(oldw != w){
            refitTextSize(getText(), w);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        refitTextSize(text, getWidth());
    }

    private void refitTextSize(CharSequence text, int width){

        if(text == null || text.length() == 0 ||width <= 0)
            return;

        int realWidth = width - getPaddingLeft() - getPaddingRight();
        float textSize = MIN_SIZE;

        mTextPaint.set(getPaint());

        while ((mPreferTextSize - textSize) > THRESHOLD) {
            float size = (mPreferTextSize + textSize) / 2;
            mTextPaint.setTextSize(size);
            if( mTextPaint.measureText(text, 0, text.length()) >= realWidth)
                mPreferTextSize = size;
            else
                textSize = size;
        }
        Log.d("TAG", "textSize = " + textSize);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        resetTextSize();
    }


    private void resetTextSize(){
        mPreferTextSize = mStartTextSize;
    }
}
