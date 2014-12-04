package com.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import com.netease.util.i.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewPagerForSlider extends ViewPagerWithIndicator
  implements be, bk
{
  private long a;
  private float b;
  private int c;
  private boolean d;
  private List<bk> e = new ArrayList();
  private float f;
  private float g;
  private int h;
  private Handler i = new Handler();
  private Runnable j = new dg(this);
  private Runnable k = new dh(this);

  public ViewPagerForSlider(Context paramContext)
  {
    super(paramContext);
    b();
  }

  public ViewPagerForSlider(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    b();
  }

  private void a(boolean paramBoolean1, boolean paramBoolean2)
  {
  }

  private void b()
  {
    this.h = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(getContext()));
  }

  private void b(boolean paramBoolean)
  {
    a(paramBoolean, true);
  }

  private boolean b(long paramLong, boolean paramBoolean)
  {
    Iterator localIterator = this.e.iterator();
    while (localIterator.hasNext())
      if (((bk)localIterator.next()).a(paramLong, paramBoolean))
        return true;
    return false;
  }

  public void a()
  {
    if (this.i != null)
    {
      this.i.removeCallbacks(this.j);
      this.i.postDelayed(this.j, 3500L);
    }
  }

  public void a(float paramFloat)
  {
    this.b = Math.max(0.0F, paramFloat);
    requestLayout();
  }

  public void a(bk parambk)
  {
    if (this.e.contains(parambk))
      return;
    this.e.add(parambk);
  }

  public void a(a parama)
  {
    a(parama, 0);
  }

  public void a(a parama, int paramInt)
  {
    ColorStateList localColorStateList = parama.b(getContext(), 2131231570);
    int m = parama.b(getContext(), 2131230751).getDefaultColor();
    int n = getChildCount();
    int i1 = 0;
    if (i1 < n)
    {
      View localView = getChildAt(i1);
      if (((ViewPager.LayoutParams)localView.getLayoutParams()).isDecor)
      {
        parama.a(localView, 2130837694);
        if (paramInt != 0)
          localView = localView.findViewById(paramInt);
        if (!(localView instanceof SlidingTabLayout))
          break label123;
        ((SlidingTabLayout)localView).a(localColorStateList);
        ((SlidingTabLayout)localView).a(new int[] { m });
      }
      while (true)
      {
        i1++;
        break;
        label123: if ((localView instanceof bj))
        {
          ((bj)localView).a(parama);
          continue;
        }
        if ((localView instanceof PagerTitleStrip))
        {
          ((PagerTitleStrip)localView).setTextColor(localColorStateList.getDefaultColor());
          continue;
        }
        if (!(localView instanceof PagerTabStrip))
          continue;
        ((PagerTabStrip)localView).setTabIndicatorColor(m);
      }
    }
  }

  public boolean a(long paramLong, boolean paramBoolean)
  {
    PagerAdapter localPagerAdapter;
    int m;
    if (this.a == paramLong)
    {
      localPagerAdapter = getAdapter();
      if (localPagerAdapter != null)
        break label31;
      m = 0;
      if (m > 1)
        break label41;
    }
    label31: label41: int n;
    View localView2;
    do
    {
      return false;
      m = localPagerAdapter.getCount();
      break;
      n = getCurrentItem();
      if (n != 0)
        break label91;
      localView2 = getChildAt(Math.max(0, 0 - this.c));
    }
    while ((localView2 != null) && (paramBoolean) && (localView2.getLeft() >= getScrollX()));
    label91: View localView1;
    do
    {
      do
        return true;
      while (n != m - 1);
      localView1 = getChildAt(-1 + getChildCount());
    }
    while ((localView1 == null) || (paramBoolean) || (localView1.getLeft() > getScrollX()));
    return false;
  }

  public boolean a(MotionEvent paramMotionEvent, boolean paramBoolean)
  {
    long l = paramMotionEvent.getDownTime();
    return (!b(l, paramBoolean)) && (!a(l, paramBoolean));
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (!checkLayoutParams(paramLayoutParams));
    for (ViewGroup.LayoutParams localLayoutParams = generateLayoutParams(paramLayoutParams); ; localLayoutParams = paramLayoutParams)
    {
      if ((paramView instanceof SlidingTabLayout))
      {
        ViewPager.LayoutParams localLayoutParams2 = (ViewPager.LayoutParams)localLayoutParams;
        localLayoutParams2.isDecor = (true | localLayoutParams2.isDecor);
        ((SlidingTabLayout)paramView).a(this);
      }
      while (true)
      {
        super.addView(paramView, paramInt, localLayoutParams);
        return;
        if (!(paramView instanceof bj))
          continue;
        ViewPager.LayoutParams localLayoutParams1 = (ViewPager.LayoutParams)localLayoutParams;
        localLayoutParams1.isDecor = (true | localLayoutParams1.isDecor);
        a((bj)paramView);
      }
    }
  }

  public void b(bk parambk)
  {
    this.e.remove(parambk);
  }

  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    boolean bool1 = super.canScroll(paramView, paramBoolean, paramInt1, paramInt2, paramInt3);
    long l;
    if (!bool1)
    {
      l = this.a;
      if (paramInt1 <= 0)
        break label44;
    }
    label44: for (boolean bool2 = true; ; bool2 = false)
    {
      bool1 = b(l, bool2);
      return bool1;
    }
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    this.d = false;
    super.dispatchDraw(paramCanvas);
    this.d = true;
    getDrawingTime();
    int m = getChildCount();
    for (int n = 0; n < m; n++)
    {
      View localView = getChildAt(n);
      if (!(localView instanceof bj))
        continue;
      ((bj)localView).a(this, paramCanvas);
    }
  }

  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    if (Build.VERSION.SDK_INT < 11);
    try
    {
      boolean bool = super.drawChild(paramCanvas, paramView, paramLong);
      return bool;
      return super.drawChild(paramCanvas, paramView, paramLong);
    }
    catch (Exception localException)
    {
      return false;
    }
    catch (Error localError)
    {
    }
    return false;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    a(true, false);
    postDelayed(this.k, 2000L);
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    b(false);
    removeCallbacks(this.k);
    if (this.i != null)
      this.i.removeCallbacks(this.j);
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 1:
    default:
    case 0:
    case 2:
    }
    try
    {
      while (true)
      {
        boolean bool = super.onInterceptTouchEvent(paramMotionEvent);
        return bool;
        this.a = paramMotionEvent.getDownTime();
        this.f = paramMotionEvent.getX();
        this.g = paramMotionEvent.getY();
        continue;
        float f1 = paramMotionEvent.getX() - this.f;
        float f2 = paramMotionEvent.getY() - this.g;
        float f3 = Math.abs(f1);
        float f4 = Math.abs(f2);
        if ((f3 <= this.h) && (f4 <= this.h))
          continue;
        b(false);
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.b > 0.0F)
    {
      int m = getDefaultSize(0, paramInt1);
      int n = (int)(m * this.b);
      paramInt1 = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
      paramInt2 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
    }
    super.onMeasure(paramInt1, paramInt2);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int m = MotionEventCompat.getActionMasked(paramMotionEvent);
    if ((m != 3) && (m != 1))
      b(true);
    try
    {
      while (true)
      {
        boolean bool = super.onTouchEvent(paramMotionEvent);
        return bool;
        b(false);
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    PagerAdapter localPagerAdapter = getAdapter();
    super.setAdapter(paramPagerAdapter);
    if (localPagerAdapter != getAdapter())
    {
      int m = getChildCount();
      int n = 0;
      if (n < m)
      {
        View localView = getChildAt(n);
        if ((localView instanceof SlidingTabLayout))
          ((SlidingTabLayout)localView).a();
        while (true)
        {
          n++;
          break;
          if (!(localView instanceof bj))
            continue;
          ((bj)localView).b();
        }
      }
    }
  }
}
