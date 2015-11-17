
package com.codezyw.common;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

@SuppressLint("DrawAllocation")
public class CanvasHelper {
    /**
     * <a href="http://blog.csdn.net/winterlc/article/details/7878175">http://
     * blog.csdn.net/winterlc/article/details/7878175</a>
     * 
     * <pre>
     * 最近学习android绘图，使用drawRect先画空心，然后用实心矩形填充，总会发现左边及上边比右边衣下边的线条粗了一圈。
     * 之前发现这和情况，将像素加减一下就行了，但总觉得不合适，因为每次加减的像素不一样，若调整界面，会导致重新计算像素大小。
     * 经过对几个像素的填充测试发现以下规律，假如绘制一个点为(10,10)，线条宽度加到1时，点的宽度会向下方（或右方）加1，也就说将绘制 [10,11) 的区间。
     * 这样的表现就是点(10,10)被绘制，而点(10,11)（其它方向暂不算）则未被绘制，则表现为1个像素的点。
     * 从此基础上再加1时，因为绘制是基于像素的，像素是最小单位，无法拆分为0.5个像素，也就是说，此时只能向一个方向加1个像素。
     * 下方（或右方）的在从0到1时已经增加，为了保持平衡，像素将向反方向扩展，绘制区间将扩展到 [9,11)。
     * 依次类推，在不断交替中，发生如下规律，width=2时y1--,width=3时,y2++,width=4时y1-=2,width=5时,y2+=2,....这样，
     * 很明显可以得出，每次增加，偶数时，左范围减去width整除2的结果，奇数时，右范围加上width整除2的结果。这样绘制过程中可以精确自动变化。
     * </pre>
     */

    /**
     * <a
     * href="http://www.2cto.com/kf/201503/379761.html">http://www.2cto.com/kf
     * /201503/379761.html</a>
     * 
     * <pre>
     * Paint 代表了Canvas上的画笔、画刷、颜料等等；
     * Paint类常用方法:
     * setARGB(int a, int r, int g, int b) // 设置 Paint对象颜色，参数一为alpha透明值
     * setAlpha(int a) // 设置alpha不透明度，范围为0~255
     * setAntiAlias(boolean aa) // 是否抗锯齿
     * setColor(int color) // 设置颜色，这里Android内部定义的有Color类包含了一些常见颜色定义
     * setTextScaleX(float scaleX) // 设置文本缩放倍数，1.0f为原始
     * setTextSize(float textSize) // 设置字体大小
     * setUnderlineText(booleanunderlineText) // 设置下划线
     * 
     * 
     * // 1、将会以颜色ARBG填充整个控件的Canvas背景
     * //mCanvas.drawARGB(122, 10, 159, 163) ;
     * // 2、将会以颜色ARBG填充整个控件的Canvas背景
     * //mCanvas.drawColor(Color.BLUE) ;
     * // 3、绘制颜色，但是要制定一个mode
     * //mCanvas.drawColor(Color.BLUE, Mode.SCREEN) ;
     * // 4、画背景，跟2等效
     * //mCanvas.drawPaint(mPaint) ;
     * // 5、画一个点
     * //mCanvas.drawPoint(23, 23, mPaint) ;
     * // 6、画很多点这里的float[] 表示{x0,y0,x1,y1,x2,y2,x3,y3.....}
     * //mCanvas.drawPoints(new float[]{10,11,10,12,10,13,10,14,10,15,10,16}, mPaint) ;
     * // 7、画线
     * //mCanvas.drawLine(...) ;
     * // 8、画长方形 Rect 和RectF的区别？
     * //精度不一样，Rect是使用int类型作为数值，RectF是使用float类型作为数值
     * //Rect r = new Rect(10,10,50,50) ;
     * //mCanvas.drawRect(r, mPaint) ;
     * //RectF rf = new RectF(10,10,50,50) ;
     * //mCanvas.drawRect(rf, mPaint) ;
     * //mCanvas.drawRect(10, 10, 50, 50, mPaint) ;
     * // 9、画椭圆 初始化RectF的参数是（left,top,right,bottom）
     * //RectF rf = new RectF(100,100 ,200 ,250) ;
     * //mCanvas.drawOval(rf, mPaint) ;
     * // 10、画圆 （圆心x0,圆心y0,半径，paint）
     * //mCanvas.drawCircle(100, 100, 50, mPaint) ;
     * // 11、画圆弧 RectF对象表明内切矩形的（left,top,right,bottom）
     * //RectF rf = new RectF(100 ,100 ,200 ,200) ;
     * // 参数(rf,startAngle ,angle ,sweepAngle ,paint) sweepAngle表明是否显示圆弧三角形 angle画多少度
     * //mCanvas.drawArc(rf, 60, 30, true, mPaint) ;
     * // 12、绘制圆角矩形 RectF是矩形的（left,top,right,bottom）
     * //RectF rf = new RectF(100 ,100 ,200 ,200) ;
     * // 50表明x方向的半径，20表示y方向的半径
     * //mCanvas.drawRoundRect(rf, 50, 20, mPaint) ;
     * // 13、画任意多边形
     * //Path path = new Path() ;
     * //path.moveTo(100, 100) ;
     * //path.lineTo(200, 200) ;
     * //path.lineTo(300, 200) ;
     * //mCanvas.drawPath(path, mPaint) ;
     * // 14、通过Path对象，也可以画其他的图形
     * //Path path = new Path() ;
     * //path.addCircle(100, 100, 20, Path.Direction.CCW) ;
     * //mCanvas.drawPath(path ,mPaint);
     * 
     * drawBitmap
     * drawText
     * drawPicture
     * 
     * Rect r = new Rect(100,100,200,200) ;
     * ByteArrayOutputStream out = new ByteArrayOutputStream();
     * Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bg) ;
     * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) ;
     * InputStream in = new ByteArrayInputStream(out.toByteArray()) ;
     * 
     * Picture picture = Picture.createFromStream(mContext.getResources().openRawResource(R.raw.bg)) ;
     * mCanvas.drawPicture(picture) ;
     * 
     * // 15、画bitmap对象
     * //mCanvas.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bg),100, 100, mPaint) ;
     * 
     * // 16、Matrix中包含了对Bitmap的处理操作
     * Matrix m = new Matrix() ;
     * m.postScale(2, 2) ;
     * m.postRotate(60) ;
     * mCanvas.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bg), m, mPaint) ;
     * 
     * // 17、画带Matrix参数的bitmap，经过Matrix对象可以对bitmap做相关的处理，比如旋转，缩放，移动等《关于Matrix的使用另行总结》
     * Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bg) ;
     * Matrix m = new Matrix() ;
     * m.postScale(2, 2) ;
     * m.postRotate(60) ;
     * m.postTranslate(300, 300) ;
     * mCanvas.drawBitmap(bitmap, m, mPaint) ;
     * //mCanvas.drawBitmap(....) ; 暂不总结
     * // 18、画文字
     * //mCanvas.drawText(123, 10, 10, mPaint) ;
     * 
     * //mCanvas.drawText(....) ; 暂不总结
     * </pre>
     */

    /**
     * <a
     * href="http://blog.csdn.net/ygc973797893/article/details/7226167">http:/
     * /blog.csdn.net/ygc973797893/article/details/7226167</a>
     * 
     * <pre>
     *  Canvas类主要实现了屏幕的绘制过程，其中包含了很多实用的方法，比如绘制一条路径、区域、贴图、画点、画线、渲染文本，下面是Canvas类常用的方法，当然Android开发网提示大家很多方法有不同的重载版本，参数更灵活。
     * 
     *   void drawRect(RectF rect, Paint paint) //绘制区域，参数一为RectF一个区域
     * 
     *   void drawPath(Path path, Paint paint) //绘制一个路径，参数一为Path路径对象 
     * 
     *   void  drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint)   //贴图，参数一就是我们常规的Bitmap对象，参数二是源区域(Android123提示这里是bitmap)，参数三是目标区域(应该在canvas的位置和大小)，参数四是Paint画刷对象，因为用到了缩放和拉伸的可能，当原始Rect不等于目标Rect时性能将会有大幅损失。 
     * 
     *   void  drawLine(float startX, float startY, float stopX, float stopY, Paint paint)  //画线，参数一起始点的x轴位置，参数二起始点的y轴位置，参数三终点的x轴水平位置，参数四y轴垂直位置，最后一个参数为Paint画刷对象。
     * 
     *   void  drawPoint(float x, float y, Paint paint) //画点，参数一水平x轴，参数二垂直y轴，第三个参数为Paint对象。
     *  
     *          void drawText(String text, float x, float y, Paint paint)  //渲染文本，Canvas类除了上面的还可以描绘文字，参数一是String类型的文本，参数二x轴，参数三y轴，参数四是Paint对象。
     * 
     *   void  drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint) //在路径上绘制文本，相对于上面第二个参数是Path路径对象
     * 
     *   从上面来看我们可以看出Canvas绘制类比较简单同时很灵活，实现一般的方法通常没有问题，同时可以叠加的处理设计出一些效果，不过细心的网友可能发现最后一个参数均为Paint对象。如果我们把Canvas当做绘画师来看，那么Paint就是我们绘画的工具，比如画笔、画刷、颜料等等
     *  Paint类常用方法:
     * 
     *  void  setARGB(int a, int r, int g, int b)  设置Paint对象颜色，参数一为alpha透明通道
     * 
     *        void  setAlpha(int a)  设置alpha不透明度，范围为0~255
     * 
     *  void  setAntiAlias(boolean aa)  //是否抗锯齿
     * 
     *  void  setColor(int color)  //设置颜色，这里Android内部定义的有Color类包含了一些常见颜色定义 
     * 
     *  void  setFakeBoldText(boolean fakeBoldText)  //设置伪粗体文本
     *   
     *        void  setLinearText(boolean linearText)  //设置线性文本
     *  
     *        PathEffect  setPathEffect(PathEffect effect)  //设置路径效果
     *  
     *        Rasterizer  setRasterizer(Rasterizer rasterizer) //设置光栅化
     *  
     *        Shader  setShader(Shader shader)  //设置阴影 
     * 
     *  void  setTextAlign(Paint.Align align)  //设置文本对齐
     * 
     *        void  setTextScaleX(float scaleX)  //设置文本缩放倍数，1.0f为原始
     *   
     *        void  setTextSize(float textSize)  //设置字体大小
     *  
     *        Typeface  setTypeface(Typeface typeface)  //设置字体，Typeface包含了字体的类型，粗细，还有倾斜、颜色等。
     * 
     *  void  setUnderlineText(boolean underlineText)  //设置下划线
     *  
     * Path路径类在位于android.graphics.Path中，Path的构造方法比较简单，如下
     * 
     *    Path cwj = new Path();  //构造方法
     * 
     *    下面我们画一个封闭的原型路径，我们使用Path类的addCircle方法
     * 
     *    cwj.addCircle(10,10,50,Direction.CW); //参数一为x轴水平位置，参数二为y轴垂直位置，第三个参数为圆形的半径，最后是绘制的方向，CW为顺时针方向，而CCW是逆时针方向。
     * 
     *     结合Android上次提到的Canvas类中的绘制方法drawPath和drawTextOnPath，我们继续可以在onDraw中加入。
     * 
     *    canvas.drawPath(cwj,paintPath); //Android123提示大家这里paintPath为路径的画刷颜色，可以见下文完整的源代码。
     * 
     *    canvas.drawTextOnPath("Android123 - CWJ",cwj,0,15,paintText); //将文字绘制到路径中去，有关drawTextOnPath的参数如下:
     * 
     *    方法原型public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint) 
     *      参数列表:
     * 
     * text  为需要在路径上绘制的文字内容。 
     * 
     * path 将文字绘制到哪个路径。 
     * 
     * hOffset   距离路径开始的距离
     *             vOffset   离路径的上下高度，这里Android开发网提示大家，该参数类型为float浮点型，除了精度为8位小数外，可以为正或负，当为正时文字在路径的圈里面，为负时在路径的圈外面。 
     *               paint  最后仍然是一个Paint对象用于制定Text本文的颜色、字体、大小等属性。
     *               
     * 有关路径类常用的方法如下:
     * 
     *  void  addArc(RectF oval, float startAngle, float sweepAngle)  //为路径添加一个多边形
     *  
     *        void  addCircle(float x, float y, float radius, Path.Direction dir)  //给path添加圆圈
     *  
     * void  addOval(RectF oval, Path.Direction dir)  //添加椭圆形
     * 
     * void  addRect(RectF rect, Path.Direction dir)  //添加一个区域
     *   
     * void  addRoundRect(RectF rect, float[] radii, Path.Direction dir)  //添加一个圆角区域
     *  
     * boolean  isEmpty()  //判断路径是否为空
     *   
     * void  transform(Matrix matrix)  //应用矩阵变换
     *  
     * void  transform(Matrix matrix, Path dst)  //应用矩阵变换并将结果放到新的路径中，即第二个参数。
     * 
     *   有关路径的高级效果大家可以使用PathEffect类.
     * </pre>
     */

    /**
     * <a href="http://blog.csdn.net/dinko321/article/details/6715290">http://
     * blog.csdn.net/dinko321/article/details/6715290</a>
     * 
     * <pre>
     *  canvas相关（渐变、阴影、path）
     * 1、setShader
     * 
     * Paint对象的setShader函数，我的感觉是设置一种方式来填充图形。
     * 
     * 可以设置为渐变，代码如下：
     * 
     * [java] view plaincopy
     * 
     *     Paint paint=new Paint();  //定义一个Paint  
     *     Shader mShader = new LinearGradient(0,0,40,60,new int[] {Color.RED,Color.GREEN,Color.BLUE},null,Shader.TileMode.REPEAT);    
     *     //新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变  
     *       
     *     paint.setShader(mShader);  
     * 
     * 
     * Shader可以有以下几种：
     * 
     * bitmapShader         位图平铺
     * 
     * linearGradient         线性渐变
     * 
     * radialGradient         圆形渐变
     * 
     * sweepGradient       角度渐变
     * 
     * composeShader    组合效果（组合以上几种）
     * 
     * 
     * 
     * 
     * 2、setShadowLayer
     * 
     * 设置了这个之后，再画出来的图形后面，会有一个阴影
     * 
     * [java] view plaincopy
     * 
     *     paint.setShaderLayer(15,10,10,Color.GRAY);  //第一个参数是阴影扩散半径，紧接着的2个参数是阴影在X和Y方向的偏移量，最后一个参数是颜色  
     * 
     * 
     * 但是这里有个问题，在画bitmap的时候，如果设置了shadowLayer，画出来的图形并不会有阴影，而是2个bitmap叠加在一起。也就是说，他的阴影层也和他本身一样。根据分析，因该是bitmap也被设置成了阴影层。暂时还没找到比较好的在图下面添加阴影的办法。
     * 
     * 
     * 
     * 
     * 3、Path的6种效果
     * 
     * CornerPathEffect              在路径的转折处是圆角，构造参数为圆角半径
     * 
     * 
     * DiscretePathEffect           不规则的锯齿线（类似心电图）
     * 
     * 构造参数：
     * 
     *     第一个是小三角的开口宽度（心电图每个波之间的宽度）
     * 
     *     一个是偏移量（心电图高度）
     * 
     * 
     * DashPathEffect                 虚线
     * 
     * 构造参数：
     * 
     *     第一个是一个数组，数组长度必须>=2，数组的值定义了宽度，比如 {20,10,5,10}，意思就是第一个实线段长20，他后面的空白长10，然后又是一个长5的实线段，然后是长度10的空白。
     * 
     *     第二个参数说是偏移量，没发现具体作用
     * 
     * 
     * PathDashPathEffect        类似上面一个，不过是由path图形组成的线段（如由三角形组成的线段，正方形组成的线段）。
     * 
     * 构造参数：
     * 
     *     第一个参数是一个path，由他定义图形。
     * 
     *     第二个是间距
     * 
     *     第三个和上面一样   
     * 
     *     第四个是变换方式，有PathDashPathEffect.Style.TRANSLATE，ROTATE，MORPH三种。第一个就是直接把图形摆出路径，第二个会依据路径旋转，第三个是依据路径自动变形
     * 
     * 
     * ComposePathEffect       把两个上面其他的方式组合起来
     * 
     * 构造参数：
     * 
     *     第一个是一个effect
     * 
     *     第二个还是一个effect
     * 
     * 
     * SumPathEffect                把两个其他方法加起来，和上面类似，差别不好描述。。。
     * </pre>
     */
    public static void ShaderAndPathEffect(Canvas canvas) {

    }

    /**
     * <a
     * href="http://blog.csdn.net/tianfeng701/article/details/7598143">http://
     * blog.csdn.net/tianfeng701/article/details/7598143</a>
     * <p>
     * <a href="http://my.csdn.net/uploads/201205/24/1337839337_2350.PNG"
     * >效果图：http://my.csdn.net/uploads/201205/24/1337839337_2350.PNG</a>
     * 
     * @param canvas
     */
    public static void onDraw(Canvas canvas) {

        // 把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();

        // 去锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        // 绘制圆形
        canvas.drawCircle(40, 40, 30, paint);
        // 绘制正方形
        canvas.drawRect(10, 80, 70, 140, paint);
        // 绘制矩形
        canvas.drawRect(10, 150, 70, 190, paint);

        RectF rel = new RectF(10, 240, 70, 270);
        // 绘制椭圆
        canvas.drawOval(rel, paint);
        // 定义一个Path对象，封闭一个三角形
        Path path1 = new Path();
        path1.moveTo(10, 340);
        path1.lineTo(70, 340);
        path1.lineTo(40, 290);
        path1.close();
        // 根据Path进行绘制，绘制三角形
        canvas.drawPath(path1, paint);

        // 定义一个Path对象，封闭一个五角星
        Path path2 = new Path();
        path2.moveTo(27, 360);
        path2.lineTo(54, 360);
        path2.lineTo(70, 392);
        path2.lineTo(40, 420);
        path2.lineTo(10, 392);
        path2.close();
        // 根据Path进行绘制，绘制五角星
        canvas.drawPath(path2, paint);

        // 设置填丛风格后进行绘制
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(120, 40, 30, paint);
        // 绘制正方形
        canvas.drawRect(90, 80, 150, 140, paint);
        // 绘制矩形
        canvas.drawRect(90, 150, 150, 190, paint);

        // 绘制圆角矩形
        RectF re2 = new RectF(90, 200, 150, 230);
        canvas.drawRoundRect(re2, 15, 15, paint);
        // 绘制椭圆
        RectF re21 = new RectF(90, 240, 150, 270);
        canvas.drawOval(re21, paint);

        Path path3 = new Path();
        path3.moveTo(90, 340);
        path3.lineTo(150, 340);
        path3.lineTo(120, 290);
        path3.close();
        // 绘制三角形
        canvas.drawPath(path3, paint);

        // 绘制五角形
        Path path4 = new Path();
        path4.moveTo(106, 360);
        path4.lineTo(134, 360);
        path4.lineTo(150, 392);
        path4.lineTo(120, 420);
        path4.lineTo(90, 392);
        path4.close();
        canvas.drawPath(path4, paint);

        // 设置渐变器后绘制
        // 为Paint设置渐变器
        Shader mShasder = new LinearGradient(0, 0, 40, 60, new int[] {
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
        }, null, Shader.TileMode.REPEAT);
        paint.setShader(mShasder);
        // 设置阴影
        paint.setShadowLayer(45, 10, 10, Color.GRAY);
        // 绘制圆形
        canvas.drawCircle(200, 40, 30, paint);
        // 绘制正方形
        canvas.drawRect(170, 80, 230, 140, paint);
        // 绘制矩形
        canvas.drawRect(170, 150, 230, 190, paint);

        // 绘制圆角的矩形
        RectF re31 = new RectF();
        canvas.drawRoundRect(re31, 15, 15, paint);

        // 绘制椭圆
        RectF re32 = new RectF();
        canvas.drawOval(re32, paint);

        // 根据Path,绘制三角形
        Path path5 = new Path();
        path5.moveTo(170, 340);
        path5.lineTo(230, 340);
        path5.lineTo(200, 290);
        path5.close();
        canvas.drawPath(path5, paint);

        // 根据PAth,进行绘制五角形
        Path path6 = new Path();
        path6.moveTo(186, 360);
        path6.lineTo(214, 360);
        path6.lineTo(230, 392);
        path6.lineTo(200, 420);
        path6.lineTo(170, 392);
        path6.close();
        canvas.drawPath(path6, paint);
    }

    /**
     * <pre>
     * 在onDraw（）中以Paint将几何图形绘制在Canvas上，   
     * 以 paint.setColor() 改变图形颜色、以 paint.setStyle()的设置来控制画出的的图形是空心还是实心。
     * 程序的最后一段，就是直接在Canvas写上文字，
     * 随着Paint对象里的属性设置，也会有不同的外观模式。
     * </pre>
     * 
     * @param canvas
     */
    protected void onDraw2(Canvas canvas) {
        /* 设置背景为白色 */
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        /* 设置paint的颜色 */
        paint.setColor(Color.RED);
        /* 设置paint的 style 为STROKE：空心 */
        paint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(3);

        /* 画一个空心圆形 */
        canvas.drawCircle(40, 40, 30, paint);
        /* 画一个空心正方形 */
        canvas.drawRect(10, 90, 70, 150, paint);
        /* 画一个空心长方形 */
        canvas.drawRect(10, 170, 70, 200, paint);
        /* 画一个空心椭圆形 */
        canvas.drawOval(new RectF(10, 220, 70, 250), paint);
        /* 画一个空心三角形 */
        Path path = new Path();
        path.moveTo(10, 330);
        path.lineTo(70, 330);
        path.lineTo(40, 270);
        path.close();
        canvas.drawPath(path, paint);
        /* 画一个空心梯形 */
        Path path1 = new Path();
        path1.moveTo(10, 410);
        path1.lineTo(70, 410);
        path1.lineTo(55, 350);
        path1.lineTo(25, 350);
        path1.close();
        canvas.drawPath(path1, paint);

        /* 设置paint的颜色 */
        paint.setColor(Color.BLUE);
        /* 设置paint 的style为 FILL：实心 */
        paint.setStyle(Paint.Style.FILL);
        /* 画一个实心圆 */
        canvas.drawCircle(120, 40, 30, paint);
        /* 画一个实心正方形 */
        canvas.drawRect(90, 90, 150, 150, paint);
        /* 画一个实心长方形 */
        canvas.drawRect(90, 170, 150, 200, paint);
        /* 画一个实心椭圆 */
        RectF re2 = new RectF(90, 220, 150, 250);
        canvas.drawOval(re2, paint);
        /* 画一个实心三角形 */
        Path path2 = new Path();
        path2.moveTo(90, 330);
        path2.lineTo(150, 330);
        path2.lineTo(120, 270);
        path2.close();
        canvas.drawPath(path2, paint);
        /* 画一个实心梯形 */
        Path path3 = new Path();
        path3.moveTo(90, 410);
        path3.lineTo(150, 410);
        path3.lineTo(135, 350);
        path3.lineTo(105, 350);
        path3.close();
        canvas.drawPath(path3, paint);

        /* 设置渐变色 */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] {
                        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
                },
                null, Shader.TileMode.REPEAT);
        // Shader.TileMode三种模式
        // REPEAT:沿着渐变方向循环重复
        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
        paint.setShader(mShader);// 用Shader中定义定义的颜色来话

        /* 画一个渐变色圆 */
        canvas.drawCircle(200, 40, 30, paint);
        /* 画一个渐变色正方形 */
        canvas.drawRect(170, 90, 230, 150, paint);
        /* 画一个渐变色长方形 */
        canvas.drawRect(170, 170, 230, 200, paint);
        /* 画一个渐变色椭圆 */
        RectF re3 = new RectF(170, 220, 230, 250);
        canvas.drawOval(re3, paint);
        /* 画一个渐变色三角形 */
        Path path4 = new Path();
        path4.moveTo(170, 330);
        path4.lineTo(230, 330);
        path4.lineTo(200, 270);
        path4.close();
        canvas.drawPath(path4, paint);
        /* 画一个渐变色梯形 */
        Path path5 = new Path();
        path5.moveTo(170, 410);
        path5.lineTo(230, 410);
        path5.lineTo(215, 350);
        path5.lineTo(185, 350);
        path5.close();
        canvas.drawPath(path5, paint);

        /* 写字 */
        paint.setTextSize(24);
        canvas.drawText("圆形", 240, 50, paint);
        canvas.drawText("正方形", 240, 120, paint);
        canvas.drawText("长方形", 240, 190, paint);
        canvas.drawText("椭圆形", 240, 250, paint);
        canvas.drawText("三角形", 240, 320, paint);
        canvas.drawText("梯形", 240, 390, paint);
    }
}
