package com.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import com.netease.util.i.a;

public abstract class Indicator extends LinearLayout
  implements Animation.AnimationListener
{
  private int a = 300;
  private int b = -1;
  private int c = -1;
  private int d = -1;
  private Animation e;
  private Handler f = new Handler();
  private Runnable g = new ah(this);

  public Indicator(Context paramContext)
  {
    super(paramContext);
    a(paramContext);
  }

  public Indicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a(paramContext);
  }

  public Indicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    a(paramContext);
  }

  private void a(Context paramContext)
  {
    setFocusable(false);
  }

  protected abstract void a();

  protected void a(a parama)
  {
  }

  protected abstract void b();

  public void b(int paramInt)
  {
    if (paramInt != this.b)
    {
      this.b = paramInt;
      a();
      this.f.removeCallbacks(this.g);
      if (this.d > 0)
        this.f.postDelayed(this.g, this.d);
      this.c = -1;
    }
  }

  public final void b(a parama)
  {
    a(parama);
  }

  public int c()
  {
    return this.b;
  }

  public void c(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.b));
    do
    {
      do
        return;
      while (paramInt == this.c);
      this.c = paramInt;
      b();
      this.f.removeCallbacks(this.g);
    }
    while (this.d <= 0);
    this.f.postDelayed(this.g, this.d);
  }

  public int d()
  {
    return this.c;
  }

  public void onAnimationEnd(Animation paramAnimation)
  {
    setVisibility(4);
  }

  public void onAnimationRepeat(Animation paramAnimation)
  {
  }

  public void onAnimationStart(Animation paramAnimation)
  {
  }
}
