package hellojava_package;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import java.util.*;

public class hellojava_name extends MIDlet {

	public static void main(String[] args){
		System.out.println("Hello, it is:");
		System.out.println(new Date());
	}
	public hellojava_name() {
		// TODO �Զ����ɹ��캯�����
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}

	protected void pauseApp() {
		// TODO �Զ����ɷ������

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO �Զ����ɷ������
		hellojava_name.main(null);//
	}

}
