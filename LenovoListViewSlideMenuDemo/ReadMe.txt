List-滑动式菜单特效
l  描述：对于item的常见操作如果不多于2个，可采用此菜单特效，通过简单的滑动即可进行相关操作；
l  基本思路：类似左右滑动抽屉特效；
l  Porting说明：
SlideMenuListViewDemo.java：demo示例
LenovoSlideMenuGroupView.java（extends ViewGroup）：包含了滑动菜单的容器；
LenovoSlidemenuListViewListener.java：应用的list注册此监听，实现滑动操作及接收菜单点击事件；

在res/values/config.xml里面可以配置：
    <!-- shdoc.lenovo.com-ui&gui-02.设计规格-02.GUI标杆设计-Vibe ROM V2.5-02.Controls -->
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: Pressed color: #1E4D1D (#259B24 40% + #191919) -->
    <drawable name="state_focused_color">#ff1e4d1d</drawable>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: Background color: Custom -->
    <drawable name="state_normal_color">#ff000000</drawable>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: Text: 16sp #FAFAFA -->
    <color name="slide_menu_item_text_color">#fffafafa</color>
    <dimen name="slide_menu_item_text_size">16sp</dimen>
    <dimen name="slide_menu_item_margin">16px</dimen>
    <dimen name="slide_menu_item_width">112.0dip</dimen>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: if there are two menu in one side -->
    <bool name="use_two_slide_menu_item_one_side">true</bool>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: if there are side menu in left side -->
    <bool name="use_slide_menu_item_for_left_side">true</bool>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: if there are side menu in right side -->
    <bool name="use_slide_menu_item_for_right_side">true</bool>