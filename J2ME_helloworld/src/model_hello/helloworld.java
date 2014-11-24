package model_hello;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;


public class helloworld extends MIDlet {

	public helloworld() {
		super ();
		// TODO 自动生成构造函数存根
		Form form = new Form("Hello World");
		form.append("Welcome to J2ME World!");
		Display.getDisplay(this).setCurrent(form);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

	protected void pauseApp() {
		// TODO 自动生成方法存根

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO 自动生成方法存根

	}

}
