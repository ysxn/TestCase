package com.amulyakhare.td.sample.sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 4:02 PM
 */
public class DrawableProvider {

    public static final int SAMPLE_RECT = 1;
    public static final int SAMPLE_ROUND_RECT = 2;
    public static final int SAMPLE_ROUND = 3;
    public static final int SAMPLE_RECT_BORDER = 4;
    public static final int SAMPLE_ROUND_RECT_BORDER = 5;
    public static final int SAMPLE_ROUND_BORDER = 6;
    public static final int SAMPLE_MULTIPLE_LETTERS = 7;
    public static final int SAMPLE_FONT = 8;
    public static final int SAMPLE_SIZE = 9;
    public static final int SAMPLE_ANIMATION = 10;
    public static final int SAMPLE_MISC = 11;

    private final ColorGenerator mGenerator;
    private final Context mContext;

    public DrawableProvider(Context context) {
        mGenerator = ColorGenerator.DEFAULT;
        mContext = context;
    }

    public TextDrawable getRect(String text) {
        return TextDrawable.builder()
                .buildRectDrawable(text, mGenerator.getColor(text));
    }

    public TextDrawable getRound(String text) {
        return TextDrawable.builder()
                .buildRoundDrawable(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundRect(String text) {
        return TextDrawable.builder()
                .buildRoundRectDrawable(text, mGenerator.getColor(text), toPx(10));
    }

    public TextDrawable getRectWithBorder(String text) {
        return TextDrawable.builder()
                .beginConfig()
                    .setWithBorder(toPx(2))
                .endConfig()
                .buildRectDrawable(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundWithBorder(String text) {
        return TextDrawable.builder()
                .beginConfig()
                    .setWithBorder(toPx(2))
                .endConfig()
                .buildRoundDrawable(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundRectWithBorder(String text) {
        return TextDrawable.builder()
                .beginConfig()
                    .setWithBorder(toPx(2))
                .endConfig()
                .buildRoundRectDrawable(text, mGenerator.getColor(text), toPx(10));
    }

    public TextDrawable getRectWithMultiLetter() {
        String text = "AK";
        return TextDrawable.builder()
                .beginConfig()
                    .setFontSize(toPx(20))
                    .toUpperCase()
                .endConfig()
                .buildRectDrawable(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundWithCustomFont() {
        String text = "Bold";
        return TextDrawable.builder()
                .beginConfig()
                    .useFont(Typeface.DEFAULT)
                    .setFontSize(toPx(15))
                    .setTextColor(0xfff58559)
                    .setBold()
                .endConfig()
                .buildRectDrawable(text, Color.DKGRAY /*toPx(5)*/);
    }

    public Drawable getRectWithCustomSize() {
        String leftText = "I";
        String rightText = "J";

        TextDrawable.IShapeBuilder builder = TextDrawable.builder()
                .beginConfig()
                    .setWidth(toPx(29))
                    .setWithBorder(toPx(2))
                .endConfig()
                .setRectShape();

        TextDrawable left = builder
                .buildDrawable(leftText, mGenerator.getColor(leftText));

        TextDrawable right = builder
                .buildDrawable(rightText, mGenerator.getColor(rightText));

        Drawable[] layerList = {
                new InsetDrawable(left, 0, 0, toPx(31), 0),
                new InsetDrawable(right, toPx(31), 0, 0, 0)
        };
        return new LayerDrawable(layerList);
    }

    public Drawable getRectWithAnimation() {
        TextDrawable.IShapeBuilder builder = TextDrawable.builder()
                .setRectShape();

        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i = 10; i > 0; i--) {
            TextDrawable frame = builder.buildDrawable(String.valueOf(i), mGenerator.getRandomColor());
            animationDrawable.addFrame(frame, 1200);
        }
        animationDrawable.setOneShot(false);
        animationDrawable.start();

        return animationDrawable;
    }

    public int toPx(int dp) {
        Resources resources = mContext.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
