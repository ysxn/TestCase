package push;

import java.util.Date;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;

public class push extends MIDlet implements CommandListener {
	private Display display;
	
    /** Soft button for exiting the game. */
    private final Command exitCmd = new Command("Exit", Command.EXIT, 2);

    
	protected void startApp() throws MIDletStateChangeException {
		display = Display.getDisplay(this);
		Form form = new Form("Push");
		form.append("This is a push example");
		form.addCommand(exitCmd);
		form.setCommandListener(this);
		display.setCurrent(form);
		scheduleMIDlet();
	}
	
    public void commandAction(Command c, Displayable d) {
        if (c == exitCmd) {
        	 
        	destroyApp(false);
            notifyDestroyed();

            return;
        }

    }
    
    
	protected void pauseApp() {
	}
	
	protected void destroyApp(boolean arg0)// throws MIDletStateChangeException {
	{	
	}
	
	private void scheduleMIDlet()
	{
		try
		{
			Date now = new Date();
			PushRegistry.registerAlarm(this.getClass().getName(),now.getTime()+10000);
		}
	    catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
