package sutras.cheng.listener;

/**
 * ��������ListView�ļ�����
 * 
 * @author chengkai
 * 
 */
public interface OnDragListViewListener {
	public void drag(int from, int to);

	public void drop(int from, int to);

	public void remove(int which);
}
