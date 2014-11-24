package testmodel;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;

public class testmodel extends MIDlet {
	private Display display;
	public testmodel() {
		// TODO 自动生成构造函数存根
		display = Display.getDisplay(this);
		System.out.println("Constructor");
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO 自动生成方法存根
		System.out.println("destroyApp is called.");
	}

	protected void pauseApp() {
		// TODO 自动生成方法存根
		System.out.println("pauseApp is called.");
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO 自动生成方法存根
		System.out.println("startApp is called.");
		Form f = new Form("test model");
		display.setCurrent(f);
	}

}
/*
private OnClickListener calcBMI = new OnClickListener() 
{ 
	public void onClick(View v) 
	{ 
		DecimalFormat nf = new DecimalFormat("0.00"); 
		EditText fieldheight = (EditText)findViewById(R.id.height); 
		EditText fieldweight = (EditText)findViewById(R.id.weight); 
		double height = Double.parseDouble(fieldheight.getText().toString())/100; 
		double weight = Double.parseDouble(fieldweight.getText().toString()); 
		double BMI = weight / (height * height); 

		TextView result = (TextView)findViewById(R.id.result); 
		result.setText("Your BMI is " + nf.format(BMI)); 

		//Give health advice 
		TextView fieldsuggest = (TextView)findViewById(R.id.suggest); 
		if(BMI > 25)
		{ 
			fieldsuggest.setText(R.string.advice_heavy); 
		}
		else if(BMI < 20)
		{ 
			fieldsuggest.setText(R.string.advice_light); 
		}
		else
		{ 
			fieldsuggest.setText(R.string.advice_average); 
		} 
	} 
}; */