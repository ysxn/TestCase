package com.amulyakhare.textdrawable;

import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * @author amulya
 * @datetime 14 Oct 2014, 3:53 PM
 */
public class TextDrawable extends ShapeDrawable {

    private final Paint mTextPaint;
    private final Paint mBorderPaint;
    private static final float SHADE_FACTOR = 0.9f;
    
    // text and color
    private final String mText;
    private final int mColor;
    
    // shape properties
    private final RectShape mShape;
    private final int mHeight;
    private final int mWidth;
    private final float mRadius;
    
    
    private final int mFontSize;
    private final int mBorderThickness;

    private TextDrawable(Builder builder) {
        super(builder.shape);

        // initial shape properties
        mShape = builder.shape;
        mHeight = builder.height;
        mWidth = builder.width;
        mRadius = builder.radius;

        // initial text and color
        mText = builder.toUpperCase ? builder.text.toUpperCase() : builder.text;
        mColor = builder.color;

        // initial text paint settings
        mFontSize = builder.fontSize;
        mTextPaint = new Paint();
        mTextPaint.setColor(builder.textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(builder.isBold);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTypeface(builder.font);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(builder.borderThickness);

        // initial border paint settings
        mBorderThickness = builder.borderThickness;
        mBorderPaint = new Paint();
        mBorderPaint.setColor(getDarkerShade(mColor));
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderThickness);

        // initial drawable paint color
        Paint paint = getPaint();
        paint.setColor(mColor);

    }

    private int getDarkerShade(int color) {
        return Color.rgb((int)(SHADE_FACTOR * Color.red(color)),
                (int)(SHADE_FACTOR * Color.green(color)),
                (int)(SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect r = getBounds();


        // draw border
        if (mBorderThickness > 0) {
            drawBorder(canvas);
        }

        int count = canvas.save();
        canvas.translate(r.left, r.top);

        // draw text
        int width = this.mWidth < 0 ? r.width() : this.mWidth;
        int height = this.mHeight < 0 ? r.height() : this.mHeight;
        int fontSize = this.mFontSize < 0 ? (Math.min(width, height) / 2) : this.mFontSize;
        mTextPaint.setTextSize(fontSize);
        canvas.drawText(mText, width / 2, height / 2 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2), mTextPaint);

        canvas.restoreToCount(count);

    }

    private void drawBorder(Canvas canvas) {
        RectF rect = new RectF(getBounds());
        rect.inset(mBorderThickness/2, mBorderThickness/2);

        if (mShape instanceof OvalShape) {
            canvas.drawOval(rect, mBorderPaint);
        }
        else if (mShape instanceof RoundRectShape) {
            canvas.drawRoundRect(rect, mRadius, mRadius, mBorderPaint);
        }
        else {
            canvas.drawRect(rect, mBorderPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mTextPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mTextPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    public static IShapeBuilder builder() {
        return new Builder();
    }

    public static class Builder implements IConfigBuilder, IShapeBuilder {

        private String text;

        private int color;

        private int borderThickness;

        private int width;

        private int height;

        private Typeface font;

        private RectShape shape;

        public int textColor;

        private int fontSize;

        private boolean isBold;

        private boolean toUpperCase;

        public float radius;

        private Builder() {
            text = "";
            color = Color.GRAY;
            textColor = Color.WHITE;
            borderThickness = 0;
            width = -1;
            height = -1;
            shape = new RectShape();
            font = Typeface.create("sans-serif-light", Typeface.NORMAL);
            fontSize = -1;
            isBold = false;
            toUpperCase = false;
        }

        public IConfigBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        public IConfigBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        public IConfigBuilder setTextColor(int color) {
            this.textColor = color;
            return this;
        }

        public IConfigBuilder setWithBorder(int thickness) {
            this.borderThickness = thickness;
            return this;
        }

        public IConfigBuilder useFont(Typeface font) {
            this.font = font;
            return this;
        }

        public IConfigBuilder setFontSize(int size) {
            this.fontSize = size;
            return this;
        }

        public IConfigBuilder setBold() {
            this.isBold = true;
            return this;
        }

        public IConfigBuilder toUpperCase() {
            this.toUpperCase = true;
            return this;
        }

        @Override
        public IConfigBuilder beginConfig() {
            return this;
        }

        @Override
        public IShapeBuilder endConfig() {
            return this;
        }

        @Override
        public IShapeBuilder setRectShape() {
            this.shape = new RectShape();
            return this;
        }

        @Override
        public IShapeBuilder setRoundShape() {
            this.shape = new OvalShape();
            return this;
        }

        @Override
        public IShapeBuilder setRoundRectShape(int radius) {
            this.radius = radius;
            float[] radii = {radius, radius, radius, radius, radius, radius, radius, radius};
            this.shape = new RoundRectShape(radii, null, null);
            return this;
        }

        @Override
        public TextDrawable buildRectDrawable(String text, int color) {
            setRectShape();
            return buildDrawable(text, color);
        }

        @Override
        public TextDrawable buildRoundRectDrawable(String text, int color, int radius) {
            setRoundRectShape(radius);
            return buildDrawable(text, color);
        }

        @Override
        public TextDrawable buildRoundDrawable(String text, int color) {
            setRoundShape();
            return buildDrawable(text, color);
        }

        @Override
        public TextDrawable buildDrawable(String text, int color) {
            this.color = color;
            this.text = text;
            return new TextDrawable(this);
        }
    }

    public interface IConfigBuilder {
        public IConfigBuilder setWidth(int width);

        public IConfigBuilder setHeight(int height);

        public IConfigBuilder setTextColor(int color);

        public IConfigBuilder setWithBorder(int thickness);

        public IConfigBuilder useFont(Typeface font);

        public IConfigBuilder setFontSize(int size);

        public IConfigBuilder setBold();

        public IConfigBuilder toUpperCase();

        public IShapeBuilder endConfig();
    }

    public static interface IShapeBuilder {

        public IConfigBuilder beginConfig();

        public IShapeBuilder setRectShape();

        public IShapeBuilder setRoundShape();

        public IShapeBuilder setRoundRectShape(int radius);
        
        public TextDrawable buildDrawable(String text, int color);

        public TextDrawable buildRectDrawable(String text, int color);

        public TextDrawable buildRoundRectDrawable(String text, int color, int radius);

        public TextDrawable buildRoundDrawable(String text, int color);
    }
}