package ru.mishaignatov.touristquiz.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Mike on 25.01.2016.
 **/
public class CircleTextView extends TextView {

    private Paint mGlowPaint;
    private Paint mTextPaint;

    private String mText;
    private int mWidth;
    private float mRadius;

    public CircleTextView(Context context) {
        super(context);
        init();
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mGlowPaint = new Paint();
        mGlowPaint.setAntiAlias(false);
        mGlowPaint.setStyle(Paint.Style.FILL);
        mGlowPaint.setColor(Color.WHITE);
        mGlowPaint.setAlpha(100);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(5);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(getTextSize());
        mText = getText().toString();

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Log.d("TAG", "text = " + text);
        mText = text.toString().trim();
        invalidate();
        //super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        // радиус ограничивающего круга
        mRadius = mWidth/2 - getPaddingRight();
        // квадратный контейнер
        Log.d("TAG", "pad = " + mRadius + " left " + getPaddingLeft() + " right " + getPaddingRight());
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // круг, в который вписан текст
        for(int i=1; i<=10; i++)
            canvas.drawCircle(mWidth/2, mWidth/2,i*mRadius/10, mGlowPaint);

        // высота строки
        float textHeight = mTextPaint.getFontSpacing();
        // измеряем длинну текста
        float widthText = mTextPaint.measureText(mText);
        if(widthText <= mRadius*2) {
            // всего одна строка помещается - рисуем ее и выходим
            canvas.drawText(mText, mWidth/2, mWidth/2, mTextPaint);
            return;
        }
        // 1 Делим строку на два, сдвигаем к ближайшему пробелу. получаем индекс пробела, близкого к середине текста
        int centerSpace = calcCenterSpace(mText);
        // 2 Сначала рисуем текст вверх  :
        String leftString = mText.substring(0, centerSpace);
        int cnt = 0; // количество прорисованных строк вверх
        float widthLine;
        int n;
        while (true) {
            // измеряем длинну строки
            widthLine = calcWidthLine(mRadius, textHeight * cnt++);
            // считаем сколько символов поместится в новой строке
            n = mTextPaint.breakText(leftString, false, widthLine, null);
            if (n == 0) // прерываем, так как строка слишком длинная
                break;
            // создаем строку
            int rest = leftString.length() - n;
            //  проверка на последнюю строку
            if (rest != 0) {
                String s = leftString.substring(leftString.length() - n);
                // обрезаем до первого пробела
                int fixed = s.indexOf(' ');
                // создаем новую аккуратную строку
                s = s.substring(fixed + 1);
                // рисуем ее на высоте соответствующую данной строке
                canvas.drawText(s, mWidth/2, mWidth/2 - textHeight * cnt + textHeight, mTextPaint);
                // сокращаем левый кусок текста на длинну отрисованной строки
                leftString = leftString.substring(0, leftString.length() - s.length());
                } else { // последняя строка
                    canvas.drawText(leftString, mWidth/2, mWidth/2 - textHeight * cnt + textHeight, mTextPaint);
                    break;
                }
            }
        // 3. Теперь рисуем вниз
        String rightString = mText.substring(centerSpace);
        cnt = 0; // количество прорисованных строк вверх
        while(true){
            widthLine = calcWidthLine(mRadius, textHeight * cnt++);
            n = mTextPaint.breakText(rightString, false, widthLine, null);
            if (n == 0) // прерываем если весь текст не поместился
                break;
            int rest = rightString.length() - n;
            if(rest != 0) {
                String s = rightString.substring(0, n);
                int fixed = s.lastIndexOf(' ');
                s = s.substring(0, fixed);
                canvas.drawText(s, mWidth/2, mWidth/2 + textHeight * cnt, mTextPaint);
                rightString = rightString.substring(s.length(), rightString.length());
            }
            else {
                canvas.drawText(rightString, mWidth/2, mWidth/2 + textHeight * cnt, mTextPaint);
                break;
            }
        }
    }

    private static int calcWidthLine(float r, float b){
        double a = Math.sqrt(r * r - b * b);
        return (int)(2*a);
    }

    private static int calcCenterSpace(String text){
        int index = text.length()/2;
        if(text.charAt(index) == ' ') return index;
        String subLeft = text.substring(0, index);
        int indexSpaceLeft = subLeft.lastIndexOf(' ');
        String subRight = text.substring(index + 2);
        int indexSpaceRight = subRight.indexOf(' ');
        if(index - indexSpaceLeft <= indexSpaceRight)
            return indexSpaceLeft;
        else
            return indexSpaceRight;
    }
}
