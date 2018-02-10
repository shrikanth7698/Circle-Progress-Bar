package com.shrikanthravi.circleprogressbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

/**
 * Created by shrikanthravi on 10/02/18.
 */



public class CircleProgressBar extends View {
    private float strokeWidth = 4;
    private float progress = 0;
    private int min = 0;
    private int max = 100;
    int i=0;

    private int startAngle = -90;
    private int color = Color.DKGRAY;
    private RectF rectF;
    private Paint backgroundPaint;
    private Paint foregroundPaint;
    Bitmap[] frames = new Bitmap[18];
    private boolean stickman = true;
    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();//Because it should recalculate its bounds
    }





    public void setColor(int color) {
        this.color = color;
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Bitmap makeScaled(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        float scaledWidth = width * 1f;
        float scaledHeight = height * 1f;
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, src.getWidth(), src.getHeight()), new RectF(0, 0, scaledWidth, scaledHeight), Matrix.ScaleToFit.CENTER);
        Bitmap output = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
        Canvas xfas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        xfas.drawBitmap(output, 0, 0, paint);

        return output;
    }
    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();

        frames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.stick0);
        frames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.stick1);
        frames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.stick2);
        frames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.stick3);
        frames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.stick4);
        frames[5] = BitmapFactory.decodeResource(getResources(), R.drawable.stick5);
        frames[6] = BitmapFactory.decodeResource(getResources(), R.drawable.stick6);
        frames[7] = BitmapFactory.decodeResource(getResources(), R.drawable.stick7);
        frames[8] = BitmapFactory.decodeResource(getResources(), R.drawable.stick8);
        frames[9] = BitmapFactory.decodeResource(getResources(), R.drawable.stick0);
        frames[10] = BitmapFactory.decodeResource(getResources(), R.drawable.stick1);
        frames[11] = BitmapFactory.decodeResource(getResources(), R.drawable.stick2);
        frames[12] = BitmapFactory.decodeResource(getResources(), R.drawable.stick3);
        frames[13] = BitmapFactory.decodeResource(getResources(), R.drawable.stick4);
        frames[14] = BitmapFactory.decodeResource(getResources(), R.drawable.stick5);
        frames[15] = BitmapFactory.decodeResource(getResources(), R.drawable.stick6);
        frames[16] = BitmapFactory.decodeResource(getResources(), R.drawable.stick7);
        frames[17] = BitmapFactory.decodeResource(getResources(), R.drawable.stick8);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleProgressBar,
                0, 0);
        //Reading values from the XML layout
        try {

            strokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_Thickness, strokeWidth);
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress);
            color = typedArray.getInt(R.styleable.CircleProgressBar_Color, color);
            min = typedArray.getInt(R.styleable.CircleProgressBar_min, min);
            max = typedArray.getInt(R.styleable.CircleProgressBar_max, max);
        } finally {
            typedArray.recycle();
        }

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStrokeCap(Paint.Cap.ROUND);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //paint.setTextSize(28);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "google_font.ttf");
        paint.setTypeface(font);
        paint.setColor(Color.BLACK);
        canvas.drawOval(rectF, backgroundPaint);
        float angle = 360 * progress / max;
        float angle1 = 360 * 1/max;
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);
        canvas.drawCircle(rectF.centerX(),rectF.top,1,foregroundPaint);

        int progressText = (int) Math.ceil(progress);
        if(stickman) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(frames[i],
                    canvas.getWidth() / 2,
                    canvas.getHeight() / 2,
                    true);
            //canvas.drawBitmap(frames[i], ((canvas.getWidth() - frames[i].getWidth()) / 2), ((canvas.getHeight() - frames[i].getHeight()) / 2), null);
            canvas.drawBitmap(scaledBitmap, ((canvas.getWidth() - scaledBitmap.getWidth()) / 2), ((canvas.getHeight() - scaledBitmap.getHeight()) / 2), paint);
            //canvas.drawBitmap(frames[i], null, new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null);
            i = i + 1;
            if (i == 17) {
                i = 0;
            }
            postInvalidateDelayed(40);
        }
        else{
            Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint1.setColor(getResources().getColor(android.R.color.transparent));
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(frames[i],
                    canvas.getWidth() / 2,
                    canvas.getHeight() / 2,
                    true);
            //canvas.drawBitmap(frames[i], ((canvas.getWidth() - frames[i].getWidth()) / 2), ((canvas.getHeight() - frames[i].getHeight()) / 2), null);
            canvas.drawBitmap(scaledBitmap, ((canvas.getWidth() - scaledBitmap.getWidth()) / 2), ((canvas.getHeight() - scaledBitmap.getHeight()) / 2), paint1);
            //canvas.drawBitmap(frames[i], null, new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null);
            i = i + 1;
            if (i == 17) {
                i = 0;
            }
            postInvalidateDelayed(40);
        }








    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);

    }



    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public int getMinValue() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
        invalidate();
    }

    public int getMaxValue() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }


    public void setProgressWithAnimation(float progress) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    public void hideStickMan(){

        this.stickman=false;
    }

    public void showStickMan(){

        this.stickman=true;

    }
}
