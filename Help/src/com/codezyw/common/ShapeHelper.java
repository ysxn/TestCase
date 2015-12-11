
package com.codezyw.common;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * <pre>
 * GradientDrawable的作用在于定于各种样式的渐变。在XML文件中使用<shape>元素定义。
 * 
 * 文件位置：
 *     res/drawable/filename.xml
 *     文件名即资源ID
 * 编译资源类型：
 *     指向 GradientDrawable.
 * 资源引用
 *     In Java: R.drawable.filename
 *     In XML: @[package:]drawable/filename
 * 语法
 * 
 *     <?xml version="1.0" encoding="utf-8"?>
 *     <shape
 *         xmlns:android="http://schemas.android.com/apk/res/android"
 *         android:shape=["rectangle" | "oval" | "line" | "ring"] >
 *         <corners
 *             android:radius="integer"
 *             android:topLeftRadius="integer"
 *             android:topRightRadius="integer"
 *             android:bottomLeftRadius="integer"
 *             android:bottomRightRadius="integer" />
 *         <gradient
 *             android:angle="integer"
 *             android:centerX="integer"
 *             android:centerY="integer"
 *             android:centerColor="integer"
 *             android:endColor="color"
 *             android:gradientRadius="integer"
 *             android:startColor="color"
 *             android:type=["linear" | "radial" | "sweep"]
 *             android:useLevel=["true" | "false"] />
 *         <padding
 *             android:left="integer"
 *             android:top="integer"
 *             android:right="integer"
 *             android:bottom="integer" />
 *         <size
 *             android:width="integer"
 *             android:height="integer" />
 *         <solid
 *             android:color="color" />
 *         <stroke
 *             android:width="integer"
 *             android:color="color"
 *             android:dashWidth="integer"
 *             android:dashGap="integer" />
 *     </shape>
 * 
 * 元素：
 * 
 *     <shape>
 *         定义这是一个GradientDrawable，必须作为根元素。
 * 
 *         属性：
 * 
 *         xmlns:android
 *             String类型。必须的，定义xml文件的命名空间，必须是"http://schemas.android.com/apk/res/android". 
 *         android:shape
 *             关键字。定义shape的值，必须是下面的之一：
 *             值   描述
 *             "rectangle"     矩阵，这也是默认的shape
 *             "oval"  椭圆
 *             "line"  一条水平的直线。这种shape必须使用 <stroke> 元素来定义这条线的宽度
 *             "ring"  圆环
 * 
 *         下面的属性只有当 android:shape="ring"才使用：
 * 
 *         android:innerRadius
 *             尺寸。 内环的半径。一个尺寸值（dip等等）或者一个尺寸资源。
 *         android:innerRadiusRatio
 *             Float类型。这个值表示内部环的比例，例如,如果android:innerRadiusRatio = " 5 ",那么内部的半径等于环的宽度除以5。这个值会被android:innerRadius重写。 默认值是9。
 *         android:thickness
 *             尺寸。环的厚度，是一个尺寸值或尺寸的资源。
 *         android:thicknessRatio
 *             Float类型。厚度的比例。例如,如果android:thicknessRatio= " 2 ",然后厚度等于环的宽度除以2。这个值是被android:innerRadius重写， 默认值是3。
 *         android:useLevel
 *             Boolean类型。如果用在 LevelListDrawable里，那么就是true。如果通常不出现则为false。
 * 
 *     <corners>
 *         为Shape创建一个圆角，只有shape是rectangle时候才使用。
 * 
 *         属性：
 * 
 *         android:radius
 *             Dimension。圆角的半径。会被下面每个特定的圆角属性重写。
 *         android:topLeftRadius
 *             Dimension。top-left 圆角的半径。
 *         android:topRightRadius
 *             Dimension。top-right 圆角的半径。
 *         android:bottomLeftRadius
 *             Dimension。 bottom-left圆角的半径。
 *         android:bottomRightRadius
 *             Dimension。bottom-right圆角的半径。
 * 
 *         注意：每个圆角半径值都必须大于1，否侧就没有圆角。
 *                  下面的话不明白，我直接设置圆角为0就可以不圆了，其余的设置有圆角，一样的可行。不知道它为什么要这么讲。
 * 
 *         （If you want specific cornersto not be rounded, a work-around is to use android:radius to set a default cornerradius greater than 1, but then override each and every corner with the values you reallywant, providing zero ("0dp") where you don't want rounded corners.）
 *     <gradient>
 *         指定这个shape的渐变颜色。
 * 
 *         属性：
 * 
 *         android:angle
 *             Integer。渐变的角度。 0 代表从 left 到 right。90 代表bottom到 top。必须是45的倍数，默认为0
 *         android:centerX
 *             Float。渐变中心的相对X坐标，在0到1.0之间。
 *         android:centerY
 *             Float。渐变中心的相对Y坐标，在0到1.0之间。
 *         android:centerColor
 *             Color。可选的颜色值。基于startColor和endColor之间。
 *         android:endColor
 *             Color。 结束的颜色。
 *         android:gradientRadius
 *             Float 。渐变的半径。只有在 android:type="radial"才使用
 *         android:startColor
 *             Color。开始的颜色值。
 *         android:type
 *             Keyword。渐变的模式，下面值之一：
 *             值   描述
 *             "linear"    线形渐变。这也是默认的模式
 *             "radial"    辐射渐变。startColor即辐射中心的颜色
 *             "sweep"     扫描线渐变。
 *         android:useLevel
 *             Boolean。如果在LevelListDrawable中使用，则为true
 * 
 * 
 *     <padding>
 * 
 *          内容与视图边界的距离
 * 
 *         属性：
 * 
 *         android:left
 *             Dimension。左边填充距离.
 *         android:top
 *             Dimension。顶部填充距离.
 *         android:right
 *             Dimension。右边填充距离.
 *         android:bottom
 *             Dimension。底部填充距离.
 * 
 *     <size>
 *         这个shape的大小。
 * 
 *         属性：
 * 
 *         android:height
 *             Dimension。这个shape的高度。
 *         android:width
 *             Dimension。这个shape的宽度。
 * 
 *         注意：默认情况下，这个shape会缩放到与他所在容器大小成正比。当你在一个ImageView中使用这个shape，你可以使用 android:scaleType="center"来限制这种缩放。
 *     <solid>
 *         填充这个shape的纯色
 * 
 *         属性：
 * 
 *         android:color
 *             Color。颜色值，十六进制数，或者一个Color资源
 * 
 *     <stroke>
 *         这个shape使用的笔画，当android:shape="line"的时候，必须设置改元素。
 * 
 *         属性：
 * 
 *         android:width
 *             Dimension。笔画的粗细。
 *         android:color
 *             Color。笔画的颜色
 *         android:dashGap
 *             Dimension。每画一条线就间隔多少。只有当android:dashWidth也设置了才有效。
 *         android:dashWidth
 *             Dimension。每画一条线的长度。只有当 android:dashGap也设置了才有效。
 * 
 * <!--   
 *     shape drawable xml文件中定义的一个几何图形，定义在res/drawable/目录下，文件名filename称为访问的资源ID  
 *     在代码中通过R.drawable.filename进行访问，在xml文件中通过@[package:]drawable/filename进行访问。  
 *  -->  
 *  <!--   
 *     android:shape=["rectangle" | "oval" | "line" | "ring"]  
 *     shape的形状，默认为矩形，可以设置为矩形（rectangle）、椭圆形(oval)、线性形状(line)、环形(ring)  
 *     下面的属性只有在android:shape="ring时可用：  
 *     android:innerRadius         尺寸，内环的半径。  
 *     android:innerRadiusRatio    浮点型，以环的宽度比率来表示内环的半径，  
 *     例如，如果android:innerRadiusRatio，表示内环半径等于环的宽度除以5，这个值是可以被覆盖的，默认为9.  
 *     android:thickness           尺寸，环的厚度  
 *     android:thicknessRatio      浮点型，以环的宽度比率来表示环的厚度，例如，如果android:thicknessRatio="2"，  
 *     那么环的厚度就等于环的宽度除以2。这个值是可以被android:thickness覆盖的，默认值是3.  
 *     android:useLevel            boolean值，如果当做是LevelListDrawable使用时值为true，否则为false.  
 *   -->  
 * <shape  
 *     xmlns:android="http://schemas.android.com/apk/res/android"  
 *     android:shape="rectangle">  
 *       
 *     <!--  
 *         圆角  
 *         android:radius              整型 半径  
 *         android:topLeftRadius       整型 左上角半径  
 *         android:topRightRadius      整型 右上角半径  
 *         android:bottomLeftRadius    整型 左下角半径  
 *         android:bottomRightRadius   整型 右下角半径  
 *      -->  
 *      <corners    
 *         android:radius="8dp"  
 *         android:topLeftRadius="5dp"  
 *         android:topRightRadius="15dp"  
 *         android:bottomLeftRadius="20dp"  
 *         android:bottomRightRadius="25dp"    
 *         />  
 *        
 *      <!--  
 *         渐变色  
 *         android:startColor  颜色值                             起始颜色  
 *         android:endColor    颜色值                             结束颜色  
 *         android:centerColor 整型                              渐变中间颜色，即开始颜色与结束颜色之间的颜色  
 *         android:angle       整型                              渐变角度(PS：当angle=0时，渐变色是从左向右。 然后逆时针方向转，当angle=90时为从下往上。angle必须为45的整数倍)  
 *         android:type        ["linear" | "radial" | "sweep"] 渐变类型(取值：linear、radial、sweep)  
 *                             linear 线性渐变，这是默认设置  
 *                             radial 放射性渐变，以开始色为中心。  
 *                             sweep 扫描线式的渐变。  
 *        android:useLevel     ["true" | "false"]              如果要使用LevelListDrawable对象，就要设置为true。设置为true无渐变。false有渐变色  
 *        android:gradientRadius 整型                            渐变色半径.当 android:type="radial" 时才使用。单独使用 android:type="radial"会报错。  
 *        android:centerX      整型                              渐变中心X点坐标的相对位置  
 *        android:centerY      整型                              渐变中心Y点坐标的相对位置  
 *     -->  
 *     <gradient  
 *         android:startColor="#FFFF0000"  
 *         android:endColor="#80FF00FF"  
 *         android:angle="45"  
 *         />   
 *           
 *     <!--  
 *         内边距，即内容与边的距离   
 *         android:left    整型 左内边距  
 *         android:top     整型 上内边距  
 *         android:right   整型 右内边距  
 *         android:bottom  整型 下内边距  
 *       -->  
 *      <padding   
 *          android:left="10dp"  
 *          android:top="10dp"  
 *          android:right="10dp"  
 *          android:bottom="10dp"  
 *          />  
 *        
 *     <!--   
 *         size 大小  
 *         android:width   整型 宽度  
 *         android:height  整型 高度  
 *     -->  
 *     <size  
 *         android:width="600dp"  
 *         />  
 *       
 *     <!--  
 *         内部填充  
 *         android:color   颜色值 填充颜色  
 *     -->  
 *     <solid   
 *         android:color="#ffff9d77"  
 *         />  
 *       
 *      <!--  
 *         描边  
 *         android:width       整型  描边的宽度  
 *         android:color       颜色值     描边的颜色  
 *         android:dashWidth   整型  表示描边的样式是虚线的宽度， 值为0时，表示为实线。值大于0则为虚线。  
 *         android:dashGap     整型  表示描边为虚线时，虚线之间的间隔 即“ - - - - ”  
 *      -->  
 *      <stroke   
 *         android:width="2dp"  
 *         android:color="#dcdcdc"    
 *         />   
 * </shape>
 * </pre>
 */
public class ShapeHelper {

    public static Drawable createRingShape(int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RING);
        shape.setColor(color);
        return shape;
    }

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
                cornerRadius, cornerRadius
                // bottom left
        };
        final Shape shape = new RoundRectShape(outerRadii, null, null);
        final ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        return drawable;
    }
}
