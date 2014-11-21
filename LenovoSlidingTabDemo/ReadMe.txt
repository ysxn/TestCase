TAB-可滚动TAB
l  描述：TAB整体超过屏幕宽度后，可以滚动TAB部分；
l  基本思路：TAB部分使用一个自定义View,利用ViewPager作为TAB下面的内容来控制滚动；
l  Porting说明：
SlidingTabDemoActivity：demo示例
DemoFragment：demo示例里面TAB下面显示内容；
SlidingTabLayout：控制TAB滚动的ScrollView，实现滑动操作及设置自定义接口，应用可以再这个里面放入主要内容；
SlidingTabStrip： TAB部分的实现ViewGroup，SlidingTabLayout会创建这个的实例；


控制TAB滚动的SlidingTabLayout提供了下列接口：
设置ViewPager：
setViewPager(ViewPager viewPager


默认TAB里面布局仅仅是个TextView，但是也可以设置自定义的TAB布局：
setCustomTabView(int layoutResId, int textViewId)

获取SlidingTabStrip的实例，这样可以自定义TAB菜单更多属性：
getTabStrip()


设置TAB里面选中的部分指示条颜色：
setCustomTabColorizer

设置TAB每个菜单部分单独的指示条颜色：
setSelectedIndicatorColors


如果是TAB里面布局没有默认是个TextView，有这些接口可以使用：
1.可以设置这个TextView文字在不同状态下的显示颜色：
setTabViewTextViewColorResId（int id）

2.设置TextView的背景资源
    public void setTabViewTextViewBackgroundResId(int id) {
        mTabViewTextViewBackgroundResId = id;
    }
    
3.设置TextView的文字大小
    public void setTabViewTextViewTextSize(int size) {
        mTabViewTextViewTextSize = size;
    }
4.设置TextView的最小高度
    public void setTabViewTextViewMinHeight(int height) {
        mTabViewTextViewMinHeight = height;
    }
5.设置TextView的最小宽度
    public void setTabViewTextViewMinWidth(int width) {
        mTabViewTextViewMinWidth = width;
    }
6.设置TextView的最大高度
    public void setTabViewTextViewMaxHeight(int height) {
        mTabViewTextViewMaxHeight = height;
    }