/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 XiNGRZ <xxx@oxo.ooo>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ooo.oxo.library.widget;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class TouchImageView extends ImageViewTouch {

    private final int edgeSlop;

    private ImageViewTouch.OnImageViewTouchDoubleTapListener mDoubleTapListener;

    public TouchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.edgeSlop = ViewConfiguration.get(context).getScaledEdgeSlop();
        setDisplayType(DisplayType.FIT_TO_SCREEN);
        setQuickScaleEnabled(true);
    }

    public void setDoubleTapListener(ImageViewTouch.OnImageViewTouchDoubleTapListener listener) {
        this.mDoubleTapListener = listener;
    }

    @Override
    protected void onViewPortChanged(float left, float top, float right, float bottom) {
        super.onViewPortChanged(0, top, right - left, bottom);
    }

    @Override
    public float getMinScale() {
        return 1f;
    }

    @Override
    public float getMaxScale() {
        return Math.max(2f, super.getMaxScale());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!super.onScroll(e1, e2, distanceX, distanceY) ||
                isOverScrolling(distanceX, distanceY) ||
                isEdgeScrolling(e1, distanceX, distanceY)) {
            requestDisallowParentInterceptTouchEvent(false);
            return false;
        } else {
            requestDisallowParentInterceptTouchEvent(true);
            return true;
        }
    }

    private boolean isOverScrolling(float distanceX, float distanceY) {
        RectF bitmapRect = getBitmapRect();
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            return (distanceX < 0 && bitmapRect.left >= mViewPort.left) ||
                    (distanceX > 0 && bitmapRect.right <= mViewPort.right);
        } else {
            return (distanceY < 0 && bitmapRect.top >= mViewPort.top) ||
                    (distanceY > 0 && bitmapRect.bottom <= mViewPort.bottom);
        }
    }

    private boolean isEdgeScrolling(MotionEvent e1, float distanceX, float distanceY) {
        if (e1 == null) {
            return false;
        }

        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            return (distanceX < 0 && e1.getX() < edgeSlop) ||
                    (distanceX > 0 && e1.getX() > getWidth() - edgeSlop);
        } else {
            return (distanceY < 0 && e1.getY() < edgeSlop) ||
                    (distanceY > 0 && e1.getY() > getHeight() - edgeSlop);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        requestDisallowParentInterceptTouchEvent(true);
        return super.onDown(e);
    }

    private void requestDisallowParentInterceptTouchEvent(boolean disallowIntercept) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @Override
    protected GestureDetector.OnGestureListener getGestureListener() {
        return new ImageViewTouch.GestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mUserScaled = true;

                float scale = getScale();
                float targetScale = onDoubleTapPost(scale, getMaxScale(), getMinScale());
                targetScale = Math.min(getMaxScale(), Math.max(targetScale, getMinScale()));
                zoomTo(targetScale, e.getX(), e.getY(), (long) mDefaultAnimationDuration);

                if (null != mDoubleTapListener) {
                    mDoubleTapListener.onDoubleTap();
                }

                return false;
            }
        };
    }

}
