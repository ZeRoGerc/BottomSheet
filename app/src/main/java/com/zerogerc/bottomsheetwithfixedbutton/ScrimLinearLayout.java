package com.zerogerc.bottomsheetwithfixedbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ScrimLinearLayout extends LinearLayout {

    @SuppressWarnings("NullableProblems") // Initialized in constructor.
    @NonNull
    private ScrimLayoutDelegate scrimLayoutDelegate;

    public ScrimLinearLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public ScrimLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrimLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrimLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        scrimLayoutDelegate = new ScrimLayoutDelegate(this);
        scrimLayoutDelegate.init();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        scrimLayoutDelegate.draw(canvas);
    }

    public void setScrimColor(@ColorInt int scrimColor) {
        scrimLayoutDelegate.setScrimColor(scrimColor);
    }

    public void setOpacity(@FloatRange(from = 0.0f, to = 1.0f) float scrimOpacity) {
        scrimLayoutDelegate.setOpacity(scrimOpacity);
    }
}
