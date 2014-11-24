package aga;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class aga extends MIDlet {

	public aga() {
		// TODO Auto-generated constructor stub
		Form form = new Form("Hello World");
		form.append("Welcome to J2ME World!");
		Display.getDisplay(this).setCurrent(form);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

}
