package dfg;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class dfgdfg extends MIDlet implements CommandListener {
	private Display display;
	
    /** Soft button for exiting the game. */
    private final Command exitCmd = new Command("Exit", Command.EXIT, 2);

	public dfgdfg() {
		// TODO �Զ����ɹ��캯�����
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO �Զ����ɷ������

	}
    public void commandAction(Command c, Displayable d) {
        if (c == exitCmd) {
        	//scheduleMIDlet();
        	//destroyApp(false);
            notifyDestroyed();

            return;
        }

    }
	protected void pauseApp() {
		// TODO �Զ����ɷ������

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO �Զ����ɷ������
		display = Display.getDisplay(this);
		Form form = new Form("Push");
		form.append("This is a push example");
		form.addCommand(exitCmd);
		form.setCommandListener(this);
		display.setCurrent(form);
	}

}
