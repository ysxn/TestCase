package com.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.AttributeSet;

public class ViewPagerWithIndicator extends ViewPager
{
  private Indicator a;
  private int b = 0;
  private ViewPager.OnPageChangeListener c;
  private boolean d;
  private PagerAdapter e;
  private boolean f;
  private int g;
  private boolean h;
  private DataSetObserver i = new di(this);
  private ViewPager.SimpleOnPageChangeListener j = new dj(this);

  public ViewPagerWithIndicator(Context paramContext)
  {
    super(paramContext);
    a();
  }

  public ViewPagerWithIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a();
  }

  private int a(int paramInt)
  {
    PagerAdapter localPagerAdapter = getAdapter();
    if (this.h)
      paramInt = a((dk)localPagerAdapter, paramInt);
    return paramInt;
  }

  public static int a(dk paramdk)
  {
    int k;
    if (paramdk == null)
      k = 0;
    do
    {
      return k;
      k = paramdk.a();
    }
    while (k <= 1);
    return k + 2;
  }

  public static int a(dk paramdk, int paramInt)
  {
    if (paramdk == null);
    int k;
    do
    {
      return paramInt;
      k = paramdk.a();
    }
    while (k <= 1);
    if (paramInt == 0)
      return k - 1;
    if (paramInt == k + 1)
      return 0;
    return paramInt - 1;
  }

  private void a()
  {
    setOnPageChangeListener(this.j);
  }

  private void b()
  {
    if ((this.e != null) && (!this.f))
    {
      this.f = true;
      this.e.registerDataSetObserver(this.i);
    }
  }

  private void c()
  {
    if ((this.e != null) && (this.f))
    {
      this.f = false;
      this.e.unregisterDataSetObserver(this.i);
    }
  }

  private void d()
  {
    Indicator localIndicator;
    if (this.a != null)
    {
      int k = i();
      this.a.b(k);
      this.a.c(this.b);
      localIndicator = this.a;
      if ((!this.d) || (k > 1))
        break label56;
    }
    label56: for (int m = 4; ; m = 0)
    {
      localIndicator.setVisibility(m);
      return;
    }
  }

  private boolean e()
  {
    int k;
    int m;
    if (this.h)
    {
      k = getCurrentItem();
      m = i();
      if (m > 1)
        break label24;
    }
    label24: 
    do
    {
      return false;
      if (k != 0)
        continue;
      f();
      setCurrentItem(m, false);
      g();
      return true;
    }
    while (k != m + 1);
    f();
    setCurrentItem(1, false);
    g();
    return true;
  }

  private void f()
  {
    PagerAdapter localPagerAdapter = getAdapter();
    if (localPagerAdapter == null);
    do
      return;
    while (!this.h);
    ((dk)localPagerAdapter).v_();
  }

  private void g()
  {
    PagerAdapter localPagerAdapter = getAdapter();
    if (localPagerAdapter == null);
    do
      return;
    while (!this.h);
    ((dk)localPagerAdapter).d();
  }

  private int h()
  {
    PagerAdapter localPagerAdapter = getAdapter();
    if (localPagerAdapter == null)
      return 0;
    if (this.h)
      return a((dk)localPagerAdapter);
    return localPagerAdapter.getCount();
  }

  private int i()
  {
    PagerAdapter localPagerAdapter = getAdapter();
    if (localPagerAdapter == null)
      return 0;
    if (this.h)
      return ((dk)localPagerAdapter).a();
    return localPagerAdapter.getCount();
  }

  public void a(Indicator paramIndicator)
  {
    this.a = paramIndicator;
    d();
  }

  public void a(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    b();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    c();
  }

  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    super.setAdapter(paramPagerAdapter);
    if ((paramPagerAdapter != null) && ((paramPagerAdapter instanceof dk)));
    for (boolean bool = true; ; bool = false)
    {
      this.h = bool;
      c();
      this.e = paramPagerAdapter;
      b();
      d();
      if (this.h)
        setCurrentItem(1);
      this.g = h();
      return;
    }
  }

  public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener)
  {
    if (paramOnPageChangeListener == this.j)
    {
      super.setOnPageChangeListener(paramOnPageChangeListener);
      return;
    }
    this.c = paramOnPageChangeListener;
  }
}
