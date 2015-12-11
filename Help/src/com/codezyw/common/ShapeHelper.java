
package com.codezyw.common;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * <pre>
 * GradientDrawable���������ڶ��ڸ�����ʽ�Ľ��䡣��XML�ļ���ʹ��<shape>Ԫ�ض��塣
 * 
 * �ļ�λ�ã�
 *     res/drawable/filename.xml
 *     �ļ�������ԴID
 * ������Դ���ͣ�
 *     ָ�� GradientDrawable.
 * ��Դ����
 *     In Java: R.drawable.filename
 *     In XML: @[package:]drawable/filename
 * �﷨
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
 * Ԫ�أ�
 * 
 *     <shape>
 *         ��������һ��GradientDrawable��������Ϊ��Ԫ�ء�
 * 
 *         ���ԣ�
 * 
 *         xmlns:android
 *             String���͡�����ģ�����xml�ļ��������ռ䣬������"http://schemas.android.com/apk/res/android". 
 *         android:shape
 *             �ؼ��֡�����shape��ֵ�������������֮һ��
 *             ֵ   ����
 *             "rectangle"     ������Ҳ��Ĭ�ϵ�shape
 *             "oval"  ��Բ
 *             "line"  һ��ˮƽ��ֱ�ߡ�����shape����ʹ�� <stroke> Ԫ�������������ߵĿ��
 *             "ring"  Բ��
 * 
 *         ���������ֻ�е� android:shape="ring"��ʹ�ã�
 * 
 *         android:innerRadius
 *             �ߴ硣 �ڻ��İ뾶��һ���ߴ�ֵ��dip�ȵȣ�����һ���ߴ���Դ��
 *         android:innerRadiusRatio
 *             Float���͡����ֵ��ʾ�ڲ����ı���������,���android:innerRadiusRatio = " 5 ",��ô�ڲ��İ뾶���ڻ��Ŀ�ȳ���5�����ֵ�ᱻandroid:innerRadius��д�� Ĭ��ֵ��9��
 *         android:thickness
 *             �ߴ硣���ĺ�ȣ���һ���ߴ�ֵ��ߴ����Դ��
 *         android:thicknessRatio
 *             Float���͡���ȵı���������,���android:thicknessRatio= " 2 ",Ȼ���ȵ��ڻ��Ŀ�ȳ���2�����ֵ�Ǳ�android:innerRadius��д�� Ĭ��ֵ��3��
 *         android:useLevel
 *             Boolean���͡�������� LevelListDrawable���ô����true�����ͨ����������Ϊfalse��
 * 
 *     <corners>
 *         ΪShape����һ��Բ�ǣ�ֻ��shape��rectangleʱ���ʹ�á�
 * 
 *         ���ԣ�
 * 
 *         android:radius
 *             Dimension��Բ�ǵİ뾶���ᱻ����ÿ���ض���Բ��������д��
 *         android:topLeftRadius
 *             Dimension��top-left Բ�ǵİ뾶��
 *         android:topRightRadius
 *             Dimension��top-right Բ�ǵİ뾶��
 *         android:bottomLeftRadius
 *             Dimension�� bottom-leftԲ�ǵİ뾶��
 *         android:bottomRightRadius
 *             Dimension��bottom-rightԲ�ǵİ뾶��
 * 
 *         ע�⣺ÿ��Բ�ǰ뾶ֵ���������1������û��Բ�ǡ�
 *                  ����Ļ������ף���ֱ������Բ��Ϊ0�Ϳ��Բ�Բ�ˣ������������Բ�ǣ�һ���Ŀ��С���֪����ΪʲôҪ��ô����
 * 
 *         ��If you want specific cornersto not be rounded, a work-around is to use android:radius to set a default cornerradius greater than 1, but then override each and every corner with the values you reallywant, providing zero ("0dp") where you don't want rounded corners.��
 *     <gradient>
 *         ָ�����shape�Ľ�����ɫ��
 * 
 *         ���ԣ�
 * 
 *         android:angle
 *             Integer������ĽǶȡ� 0 ����� left �� right��90 ����bottom�� top��������45�ı�����Ĭ��Ϊ0
 *         android:centerX
 *             Float���������ĵ����X���꣬��0��1.0֮�䡣
 *         android:centerY
 *             Float���������ĵ����Y���꣬��0��1.0֮�䡣
 *         android:centerColor
 *             Color����ѡ����ɫֵ������startColor��endColor֮�䡣
 *         android:endColor
 *             Color�� ��������ɫ��
 *         android:gradientRadius
 *             Float ������İ뾶��ֻ���� android:type="radial"��ʹ��
 *         android:startColor
 *             Color����ʼ����ɫֵ��
 *         android:type
 *             Keyword�������ģʽ������ֵ֮һ��
 *             ֵ   ����
 *             "linear"    ���ν��䡣��Ҳ��Ĭ�ϵ�ģʽ
 *             "radial"    ���佥�䡣startColor���������ĵ���ɫ
 *             "sweep"     ɨ���߽��䡣
 *         android:useLevel
 *             Boolean�������LevelListDrawable��ʹ�ã���Ϊtrue
 * 
 * 
 *     <padding>
 * 
 *          ��������ͼ�߽�ľ���
 * 
 *         ���ԣ�
 * 
 *         android:left
 *             Dimension�����������.
 *         android:top
 *             Dimension������������.
 *         android:right
 *             Dimension���ұ�������.
 *         android:bottom
 *             Dimension���ײ�������.
 * 
 *     <size>
 *         ���shape�Ĵ�С��
 * 
 *         ���ԣ�
 * 
 *         android:height
 *             Dimension�����shape�ĸ߶ȡ�
 *         android:width
 *             Dimension�����shape�Ŀ�ȡ�
 * 
 *         ע�⣺Ĭ������£����shape�����ŵ���������������С�����ȡ�������һ��ImageView��ʹ�����shape�������ʹ�� android:scaleType="center"�������������š�
 *     <solid>
 *         ������shape�Ĵ�ɫ
 * 
 *         ���ԣ�
 * 
 *         android:color
 *             Color����ɫֵ��ʮ��������������һ��Color��Դ
 * 
 *     <stroke>
 *         ���shapeʹ�õıʻ�����android:shape="line"��ʱ�򣬱������ø�Ԫ�ء�
 * 
 *         ���ԣ�
 * 
 *         android:width
 *             Dimension���ʻ��Ĵ�ϸ��
 *         android:color
 *             Color���ʻ�����ɫ
 *         android:dashGap
 *             Dimension��ÿ��һ���߾ͼ�����١�ֻ�е�android:dashWidthҲ�����˲���Ч��
 *         android:dashWidth
 *             Dimension��ÿ��һ���ߵĳ��ȡ�ֻ�е� android:dashGapҲ�����˲���Ч��
 * 
 * <!--   
 *     shape drawable xml�ļ��ж����һ������ͼ�Σ�������res/drawable/Ŀ¼�£��ļ���filename��Ϊ���ʵ���ԴID  
 *     �ڴ�����ͨ��R.drawable.filename���з��ʣ���xml�ļ���ͨ��@[package:]drawable/filename���з��ʡ�  
 *  -->  
 *  <!--   
 *     android:shape=["rectangle" | "oval" | "line" | "ring"]  
 *     shape����״��Ĭ��Ϊ���Σ���������Ϊ���Σ�rectangle������Բ��(oval)��������״(line)������(ring)  
 *     ���������ֻ����android:shape="ringʱ���ã�  
 *     android:innerRadius         �ߴ磬�ڻ��İ뾶��  
 *     android:innerRadiusRatio    �����ͣ��Ի��Ŀ�ȱ�������ʾ�ڻ��İ뾶��  
 *     ���磬���android:innerRadiusRatio����ʾ�ڻ��뾶���ڻ��Ŀ�ȳ���5�����ֵ�ǿ��Ա����ǵģ�Ĭ��Ϊ9.  
 *     android:thickness           �ߴ磬���ĺ��  
 *     android:thicknessRatio      �����ͣ��Ի��Ŀ�ȱ�������ʾ���ĺ�ȣ����磬���android:thicknessRatio="2"��  
 *     ��ô���ĺ�Ⱦ͵��ڻ��Ŀ�ȳ���2�����ֵ�ǿ��Ա�android:thickness���ǵģ�Ĭ��ֵ��3.  
 *     android:useLevel            booleanֵ�����������LevelListDrawableʹ��ʱֵΪtrue������Ϊfalse.  
 *   -->  
 * <shape  
 *     xmlns:android="http://schemas.android.com/apk/res/android"  
 *     android:shape="rectangle">  
 *       
 *     <!--  
 *         Բ��  
 *         android:radius              ���� �뾶  
 *         android:topLeftRadius       ���� ���Ͻǰ뾶  
 *         android:topRightRadius      ���� ���Ͻǰ뾶  
 *         android:bottomLeftRadius    ���� ���½ǰ뾶  
 *         android:bottomRightRadius   ���� ���½ǰ뾶  
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
 *         ����ɫ  
 *         android:startColor  ��ɫֵ                             ��ʼ��ɫ  
 *         android:endColor    ��ɫֵ                             ������ɫ  
 *         android:centerColor ����                              �����м���ɫ������ʼ��ɫ�������ɫ֮�����ɫ  
 *         android:angle       ����                              ����Ƕ�(PS����angle=0ʱ������ɫ�Ǵ������ҡ� Ȼ����ʱ�뷽��ת����angle=90ʱΪ�������ϡ�angle����Ϊ45��������)  
 *         android:type        ["linear" | "radial" | "sweep"] ��������(ȡֵ��linear��radial��sweep)  
 *                             linear ���Խ��䣬����Ĭ������  
 *                             radial �����Խ��䣬�Կ�ʼɫΪ���ġ�  
 *                             sweep ɨ����ʽ�Ľ��䡣  
 *        android:useLevel     ["true" | "false"]              ���Ҫʹ��LevelListDrawable���󣬾�Ҫ����Ϊtrue������Ϊtrue�޽��䡣false�н���ɫ  
 *        android:gradientRadius ����                            ����ɫ�뾶.�� android:type="radial" ʱ��ʹ�á�����ʹ�� android:type="radial"�ᱨ��  
 *        android:centerX      ����                              ��������X����������λ��  
 *        android:centerY      ����                              ��������Y����������λ��  
 *     -->  
 *     <gradient  
 *         android:startColor="#FFFF0000"  
 *         android:endColor="#80FF00FF"  
 *         android:angle="45"  
 *         />   
 *           
 *     <!--  
 *         �ڱ߾࣬��������ߵľ���   
 *         android:left    ���� ���ڱ߾�  
 *         android:top     ���� ���ڱ߾�  
 *         android:right   ���� ���ڱ߾�  
 *         android:bottom  ���� ���ڱ߾�  
 *       -->  
 *      <padding   
 *          android:left="10dp"  
 *          android:top="10dp"  
 *          android:right="10dp"  
 *          android:bottom="10dp"  
 *          />  
 *        
 *     <!--   
 *         size ��С  
 *         android:width   ���� ���  
 *         android:height  ���� �߶�  
 *     -->  
 *     <size  
 *         android:width="600dp"  
 *         />  
 *       
 *     <!--  
 *         �ڲ����  
 *         android:color   ��ɫֵ �����ɫ  
 *     -->  
 *     <solid   
 *         android:color="#ffff9d77"  
 *         />  
 *       
 *      <!--  
 *         ���  
 *         android:width       ����  ��ߵĿ��  
 *         android:color       ��ɫֵ     ��ߵ���ɫ  
 *         android:dashWidth   ����  ��ʾ��ߵ���ʽ�����ߵĿ�ȣ� ֵΪ0ʱ����ʾΪʵ�ߡ�ֵ����0��Ϊ���ߡ�  
 *         android:dashGap     ����  ��ʾ���Ϊ����ʱ������֮��ļ�� ���� - - - - ��  
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
