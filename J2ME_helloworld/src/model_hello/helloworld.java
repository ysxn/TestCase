package model_hello;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;


public class helloworld extends MIDlet {

	public helloworld() {
		super ();
		// TODO �Զ����ɹ��캯�����
		Form form = new Form("Hello World");
		form.append("Welcome to J2ME World!");
		Display.getDisplay(this).setCurrent(form);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}

	protected void pauseApp() {
		// TODO �Զ����ɷ������

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}

}
