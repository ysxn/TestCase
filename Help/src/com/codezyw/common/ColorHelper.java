
package com.codezyw.common;

public class ColorHelper {
    /** 黄色 **/
    public static final int YELLOW = 0xFFFFFF00;
    /** 青色 **/
    public static final int CYAN = 0xFF00FFFF;
    /** 品红 **/
    public static final int FUCHSIN = 0xFFFF00FF;
    /** 白色 **/
    public static final int WHITE = 0xFFFFFFFF;
    /** 黑色 **/
    public static final int BLACK = 0xFF000000;
    /** 红色 **/
    public static final int RED = 0xFFFF0000;
    /** 绿色 **/
    public static final int GREEN = 0xFF00FF00;
    /** 蓝色 **/
    public static final int BLUE = 0xFF0000FF;

    public static int getColor(byte alpha, byte red, byte green, byte blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * <a href="http://www.cnblogs.com/zxjay/archive/2008/07/14/1266950.html">
     * http://www.cnblogs.com/zxjay/archive/2008/07/14/1266950.html</a>
     * 颜色表示模式有多种，比较常用的(如Windows调色板)有减色系统的RGB(红，绿，蓝)和HSB(色调，饱和度，亮度)，
     * 这里简单实现RGB跟HSB之间的转换。 简单算法如下：
     * <p>
     * HSB用float数据类型表示
     */
    public static void RGB2HSB(int r, int g, int b, float hue, float sat, float bri)
    {
        int minval = Math.min(r, Math.min(g, b));
        int maxval = Math.max(r, Math.max(g, b));

        // bri
        bri = (float) (maxval + minval) / 510;

        // sat
        if (maxval == minval)
        {
            sat = 0.0f;
        }
        else
        {
            int sum = maxval + minval;

            if (sum > 255)
            {
                sum = 510 - sum;
            }

            sat = (float) (maxval - minval) / sum;
        }

        // hue
        if (maxval == minval)
        {
            hue = 0.0f;
        }
        else
        {
            float diff = (float) (maxval - minval);
            float rnorm = (maxval - r) / diff;
            float gnorm = (maxval - g) / diff;
            float bnorm = (maxval - b) / diff;

            hue = 0.0f;

            if (r == maxval)
            {
                hue = 60.0f * (6.0f + bnorm - gnorm);
            }

            if (g == maxval)
            {
                hue = 60.0f * (2.0f + rnorm - bnorm);
            }

            if (b == maxval)
            {
                hue = 60.0f * (4.0f + gnorm - rnorm);
            }

            if (hue > 360.0f)
            {
                hue = hue - 360.0f;
            }
        }
    }

    /**
     * <a href="http://www.cnblogs.com/zxjay/archive/2008/07/14/1266950.html">
     * http://www.cnblogs.com/zxjay/archive/2008/07/14/1266950.html</a>
     * 颜色表示模式有多种，比较常用的(如Windows调色板)有减色系统的RGB(红，绿，蓝)和HSB(色调，饱和度，亮度)，
     * 这里简单实现RGB跟HSB之间的转换。 简单算法如下：
     * <p>
     * HSB用int数据类型表示，同Winform中的ColorDialog中HSB的表示
     */
    public static void RGB2HSB(int r, int g, int b, int hue, int sat, int bri) {
        float fHue = 0, fSat = 0, fBri = 0;
        RGB2HSB(r, g, b, fHue, fSat, fBri);
        hue = (int) ((fHue / 360.0f) * 240 + 0.5);
        sat = (int) (fSat * 241 + 0.5);
        bri = (int) (fBri * 241 + 0.5);
        if (hue > 239) {
            hue = 239;
        }
        if (sat > 240) {
            sat = 240;
        }
        if (bri > 240) {
            bri = 240;
        }
    }

    /**
     * <a href="http://www.cnblogs.com/zxjay/archive/2008/07/14/1266950.html">
     * http://www.cnblogs.com/zxjay/archive/2008/07/14/1266950.html</a>
     * 颜色表示模式有多种，比较常用的(如Windows调色板)有减色系统的RGB(红，绿，蓝)和HSB(色调，饱和度，亮度)，
     * 这里简单实现RGB跟HSB之间的转换。 简单算法如下：
     * <p>
     * 传入的HSB用int型表示，如果用float型表示，可将下面的转换成float过程去掉
     */
    public static void HSB2RGB(int hue, int sat, int bri, int r, int g, int b) {
        // begin:HSB转换为float
        if (hue > 239) {
            hue = 239;
        } else {
            if (hue < 0) {
                hue = 0;
            }
        }

        if (sat > 240) {
            sat = 240;
        } else {
            if (sat < 0) {
                sat = 0;
            }
        }

        if (bri > 240) {
            bri = 240;
        } else {
            if (bri < 0) {
                bri = 0;
            }
        }

        float H = hue / 239.0f;
        float S = sat / 240.0f;
        float L = bri / 240.0f;

        // end:HSB转换为float

        float red = 0, green = 0, blue = 0;
        float d1, d2;

        if (L == 0) {
            red = green = blue = 0;
        } else {
            if (S == 0) {
                red = green = blue = L;
            } else {
                d2 = (L <= 0.5f) ? L * (1.0f + S) : L + S - (L * S);
                d1 = 2.0f * L - d2;

                float[] d3 = new float[] {
                        H + 1.0f / 3.0f, H, H - 1.0f / 3.0f
                };
                float[] rgb = new float[] {
                        0, 0, 0
                };

                for (int i = 0; i < 3; i++) {
                    if (d3[i] < 0) {
                        d3[i] += 1.0f;
                    }

                    if (d3[i] > 1.0f) {
                        d3[i] -= 1.0f;
                    }

                    if (6.0f * d3[i] < 1.0f) {
                        rgb[i] = d1 + (d2 - d1) * d3[i] * 6.0f;
                    } else {
                        if (2.0f * d3[i] < 1.0f) {
                            rgb[i] = d2;
                        } else {
                            if (3.0f * d3[i] < 2.0f) {
                                rgb[i] = (d1 + (d2 - d1) * ((2.0f / 3.0f) - d3[i]) * 6.0f);
                            } else {
                                rgb[i] = d1;
                            }
                        }
                    }
                }
                red = rgb[0];
                green = rgb[1];
                blue = rgb[2];
            }
        }

        red = 255.0f * red;
        green = 255.0f * green;
        blue = 255.0f * blue;

        if (red < 1) {
            red = 0.0f;
        } else {
            if (red > 255.0f) {
                red = 255.0f;
            }
        }

        if (green < 1) {
            green = 0.0f;
        } else {
            if (green > 255.0f) {
                green = 255.0f;
            }
        }

        if (blue < 1) {
            blue = 0.0f;
        } else {
            if (blue > 255.0f) {
                blue = 255.0f;
            }
        }

        r = (int) (red + 0.5);
        g = (int) (green + 0.5);
        b = (int) (blue + 0.5);
    }
}
