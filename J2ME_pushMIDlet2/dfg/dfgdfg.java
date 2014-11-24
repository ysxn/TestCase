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
		// TODO 自动生成构造函数存根
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO 自动生成方法存根

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
		// TODO 自动生成方法存根

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO 自动生成方法存根
		display = Display.getDisplay(this);
		Form form = new Form("Push");
		form.append("This is a push example");
		form.addCommand(exitCmd);
		form.setCommandListener(this);
		display.setCurrent(form);
	}

}
