TAB-�ɹ���TAB
l  ������TAB���峬����Ļ��Ⱥ󣬿��Թ���TAB���֣�
l  ����˼·��TAB����ʹ��һ���Զ���View,����ViewPager��ΪTAB��������������ƹ�����
l  Porting˵����
SlidingTabDemoActivity��demoʾ��
DemoFragment��demoʾ������TAB������ʾ���ݣ�
SlidingTabLayout������TAB������ScrollView��ʵ�ֻ��������������Զ���ӿڣ�Ӧ�ÿ�����������������Ҫ���ݣ�
SlidingTabStrip�� TAB���ֵ�ʵ��ViewGroup��SlidingTabLayout�ᴴ�������ʵ����


����TAB������SlidingTabLayout�ṩ�����нӿڣ�
����ViewPager��
setViewPager(ViewPager viewPager


Ĭ��TAB���沼�ֽ����Ǹ�TextView������Ҳ���������Զ����TAB���֣�
setCustomTabView(int layoutResId, int textViewId)

��ȡSlidingTabStrip��ʵ�������������Զ���TAB�˵��������ԣ�
getTabStrip()


����TAB����ѡ�еĲ���ָʾ����ɫ��
setCustomTabColorizer

����TABÿ���˵����ֵ�����ָʾ����ɫ��
setSelectedIndicatorColors


�����TAB���沼��û��Ĭ���Ǹ�TextView������Щ�ӿڿ���ʹ�ã�
1.�����������TextView�����ڲ�ͬ״̬�µ���ʾ��ɫ��
setTabViewTextViewColorResId��int id��

2.����TextView�ı�����Դ
    public void setTabViewTextViewBackgroundResId(int id) {
        mTabViewTextViewBackgroundResId = id;
    }
    
3.����TextView�����ִ�С
    public void setTabViewTextViewTextSize(int size) {
        mTabViewTextViewTextSize = size;
    }
4.����TextView����С�߶�
    public void setTabViewTextViewMinHeight(int height) {
        mTabViewTextViewMinHeight = height;
    }
5.����TextView����С���
    public void setTabViewTextViewMinWidth(int width) {
        mTabViewTextViewMinWidth = width;
    }
6.����TextView�����߶�
    public void setTabViewTextViewMaxHeight(int height) {
        mTabViewTextViewMaxHeight = height;
    }