List-����ʽ�˵���Ч
l  ����������item�ĳ����������������2�����ɲ��ô˲˵���Ч��ͨ���򵥵Ļ������ɽ�����ز�����
l  ����˼·���������һ���������Ч��
l  Porting˵����
SlideMenuListViewDemoActivity.java��demoʾ��
LenovoListViewSlideMenuGroupView.java��extends ViewGroup���������˻����˵���������
LenovoListViewSlideMenuListener.java��Ӧ�õ�listע��˼�����ʵ�ֻ������������ղ˵�����¼���
LenovoListViewSlideMenuAdapter.java �����adapter��Ӧ�ÿ��Լ̳����������Ҫ��ʾ��list view item����

��res/values/config.xml����������ã�
    <!-- shdoc.lenovo.com-ui&gui-02.��ƹ��-02.GUI������-Vibe ROM V2.5-02.Controls -->
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: Pressed color: #1E4D1D (#259B24 40% + #191919) -->
    <drawable name="state_focused_color">#ff1e4d1d</drawable>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: Background color: Custom -->
    <drawable name="state_normal_color">#ff000000</drawable>
    
    <!-- VIBE2.5 GuidelineV0.3 041022.pdf page-49: Text: 16sp #FAFAFA -->
    <color name="slide_menu_item_text_color">#fffafafa</color>
    <dimen name="slide_menu_item_text_size">16sp</dimen>
    <dimen name="slide_menu_item_margin">16px</dimen>
    <dimen name="slide_menu_item_width">112.0dip</dimen>
    

�����˵�������LenovoListViewSlideMenuGroupView.java�ṩ�����нӿڣ�
������ʾ���Ҳ˵��Ƿ���ʾ��
setHideLeftSlideMenu��bool��
setHideRightSlideMenu��bool��

��ȡ�˵��������ò˵���ʾ�����ֺ�ͼ����������ԣ�
getSlideMenuLeftSideText����
getSlideMenuRightSideText����
getSlideMenuLeftSideIcon����
getSlideMenuRightSideIcon����