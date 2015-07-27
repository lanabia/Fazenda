package com.pitombatech.photocomb.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;

import com.pitombatech.photocomb.R;

/**
 * Created by lana on 19/07/15.
 * based on http://android-developers.blogspot.com.br/2010/06/making-sense-of-multitouch.html
 */
public class BackImage extends View {

    private static final int INVALID_POINTER_ID = -1;

    private Drawable mIcon;
    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = INVALID_POINTER_ID;

    private float mAngle;
    private float mLastAngle;
    private float[] mFirstDoubleTouchPointer;

    private ScaleGestureDetector mScaleDetector; // listener scale movement

    private float mScaleFactor = 1.f;

    public BackImage(Context context) {
        this(context, null, 0);
    }

    public BackImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mIcon = context.getResources().getDrawable(R.drawable.a);
        mIcon.setBounds(0, 0, mIcon.getIntrinsicWidth(), mIcon.getIntrinsicHeight());

        mFirstDoubleTouchPointer = new float[]{0f, 0f, 100f, 0f};

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;

                mActivePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                if (twoTouch(ev)) {
                    mFirstDoubleTouchPointer = new float[]{ev.getX(0), ev.getY(0), ev.getX(1), ev.getY(1)};
                    Log.d("Lana", "mFirstDoubleTouchPointerX: " + mFirstDoubleTouchPointer[0] + " " + mFirstDoubleTouchPointer[1] + " " + mFirstDoubleTouchPointer[2] + " " + mFirstDoubleTouchPointer[3] + " ");
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress() && !twoTouch(ev)) {
                    mPosX += dx;
                    mPosY += dy;

                    invalidate();
                }

                mLastTouchX = x;
                mLastTouchY = y;

                // to get angle of rotation
                if (twoTouch(ev)) {
                    mAngle = Math.abs(getAngle(ev)) > 1 ? (getAngle(ev)) : 0;
                    Log.d("Lana", "Angle: " + mAngle);

                    invalidate();
                }

                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                if (twoTouch(ev)) {
                    mLastAngle = getAngle(ev);
                    Log.d("Lana", "LastAngle: " + mLastAngle);
                }
                break;
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Log.d("Lana","Redraw");

        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.rotate(mAngle, canvas.getWidth() / 2, canvas.getHeight() / 2);
        mIcon.draw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }

    /**
     * Determine the degree between the first two fingers
     */
    private float getAngle(MotionEvent event) {
        float angle = 0f;
        double radians = 0f;

        double x1_p = event.getX(1);
        double x0_p = event.getX(0);
        double y1_p = event.getY(1);
        double y0_p = event.getY(0);

        double x1_i = mFirstDoubleTouchPointer[2];
        double x0_i = mFirstDoubleTouchPointer[0];
        double y1_i = mFirstDoubleTouchPointer[3];
        double y0_i = mFirstDoubleTouchPointer[1];

        // Last solution (arcTan to describe the angle)
        //double aPos = x1_p - x0_p;
        //double bPos = y1_p - y0_p;
        //double aInicial = x1_i - x0_i;
        //double bInicial = y1_i - y0_i;
        //double radians = Math.atan((bInicial * aPos - bPos * aInicial) / (aInicial * aPos + bInicial * bPos));


        Point2D i0 = new Point2D(x0_i, y0_i);
        Point2D i1 = new Point2D(x1_i, y1_i);
        Point2D p0 = new Point2D(x0_p, y0_p);
        Point2D p1 = new Point2D(x1_p, y1_p);

        Vector2D v_i = new Vector2D(i1, i0);
        Vector2D v_p = new Vector2D(p1, p0);

        radians = v_p.angle() - v_i.angle();

        angle = (float) Math.toDegrees(radians) + mLastAngle;

        if (angle > 360)
            angle = angle - 360;
        else if (angle < -360)
            angle = angle + 360;

        return angle;
    }

    /**
     * To get twoTouch gesture on the screen
     *
     * @param event
     * @return true if it has occured a two touch gesture on screen
     */
    private boolean twoTouch(MotionEvent event) {
        return (event.getPointerCount() == 2) ? true : false;
    }
}
