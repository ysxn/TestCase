package helloworld;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class helloworld extends MIDlet {

	public helloworld() {
		// TODO Auto-generated method stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		Form form = new Form("Hello World");
		form.append("Welcome to J2ME World!");
		Display.getDisplay(this).setCurrent(form);
	}

}
