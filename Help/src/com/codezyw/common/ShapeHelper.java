
package com.codezyw.common;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

public class ShapeHelper {
    
    
    public static ShapeDrawable createRectShape(int color) {
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(color);
        return shape;
    }
    
    public static ShapeDrawable createRectShape(int width, int height, int color) {
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.setIntrinsicHeight(height);
        shape.setIntrinsicWidth(width);
        shape.getPaint().setColor(color);
        return shape;
    }
    
    public static ShapeDrawable createRoundRectShape(final float cornerRadius, int color) {
        final float[] outerRadii = new float[] {
                cornerRadius, cornerRadius, // top left
                cornerRadius, cornerRadius, // top right
                cornerRadius, cornerRadius, // bottom right
                cornerRadius, cornerRadius  // bottom left
        };
        final Shape shape = new RoundRectShape(outerRadii, null, null);
        final ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        return drawable;
    }
}
