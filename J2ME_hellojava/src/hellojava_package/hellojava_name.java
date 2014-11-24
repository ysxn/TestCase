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
		// TODO 自动生成构造函数存根
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

	protected void pauseApp() {
		// TODO 自动生成方法存根

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO 自动生成方法存根
		hellojava_name.main(null);//
	}

}
