package cn.kriesz.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * @author Krisez
 */
public class PointsView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean isRunning;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private final List<Circle> mCircleList = new ArrayList<>();

    public PointsView(Context context) {
        this(context, null);
    }

    public PointsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        //设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常亮
        setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        isRunning = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (canDraw) {
                draw();
            }
        }
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLACK);
                for (Circle c : mCircleList) {
                    mPaint.setColor(c.color);
                    mCanvas.drawCircle(c.x, c.y, c.radius, mPaint);
                    c.update(mWidth, mHeight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (isRunning) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private boolean canDraw;

    public void start() {

        if (mCircleList.isEmpty()) {
            insert();
        }
        canDraw = true;
    }

    public void stop() {
        canDraw = false;
    }

    private String randColor() {
        String r = Integer.toHexString(new Random().nextInt(256)).toUpperCase(Locale.getDefault());
        String g = Integer.toHexString(new Random().nextInt(256)).toUpperCase(Locale.getDefault());
        String b = Integer.toHexString(new Random().nextInt(256)).toUpperCase(Locale.getDefault());
        String a = Integer.toHexString(new Random().nextInt(256)).toUpperCase(Locale.getDefault());
        if (r.length() == 1) {
            r = "0" + r;
        }
        if (b.length() == 1) {
            b = "0" + b;
        }
        if (g.length() == 1) {
            g = "0" + g;
        }
        if (a.length() == 1) {
            a = "0" + a;
        }
        return "#" + a + r + g + b;
    }

    public void insert() {
        Random random = new Random();
        String color = randColor();
        mCircleList.add(new Circle(new Random().nextInt(mWidth), random.nextInt(mHeight), Color.parseColor(color), random.nextInt(20), random.nextInt(90) + 10));
    }

    public boolean hasRun(){
        return canDraw;
    }
}
