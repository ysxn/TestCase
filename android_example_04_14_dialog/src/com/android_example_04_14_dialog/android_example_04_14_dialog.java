package com.android_example_04_14_dialog;

import android.app.Activity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.*;
import android.view.LayoutInflater;
import android.view.View;

public class android_example_04_14_dialog extends Activity {
	ProgressDialog m_Dialog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Dialog dialog = new AlertDialog.Builder(android_example_04_14_dialog.this)
        .setTitle("login")
        .setMessage("you need login")
        .setPositiveButton("yes", 
        		new DialogInterface.OnClickListener()
        {
        	public void onClick(DialogInterface dialog, int whichButton)
        	{
        		LayoutInflater factory = LayoutInflater.from(android_example_04_14_dialog.this);
        		final View DialogView = factory.inflate(R.layout.dialog, null);
        		AlertDialog dlg = new AlertDialog.Builder(android_example_04_14_dialog.this)
        		.setTitle("loginning")
        		.setView(DialogView)
        		.setPositiveButton("yes", 
        				new DialogInterface.OnClickListener()
        		{
        			public void onClick(DialogInterface dialog, int whichButton)
        			{
        				m_Dialog = ProgressDialog.show
        						(android_example_04_14_dialog.this,
        						"pls wait...",
        						"is loginning...",
        						true
        						);
        				new Thread()
        				{
        					public void run()
        					{
        						try
        						{
        							sleep(3000);
        						}
        						catch (Exception e)
        						{
        							e.printStackTrace();
        						}
        						finally
        						{
        							m_Dialog.dismiss();
        						}
        					}
        				}.start();
        			}
        		})
        		.setNegativeButton("cancel",
        				new DialogInterface.OnClickListener()
        		{
        			public void onClick(DialogInterface dialog, int whichButton)
        			{
        				android_example_04_14_dialog.this.finish();
        			}
        		})
        		.create();
        		dlg.show();
        	}
        })
        .setNeutralButton("quit",
        		new DialogInterface.OnClickListener()
        {
			public void onClick(DialogInterface dialog, int whichBuuton)
			{
				android_example_04_14_dialog.this.finish();
			}
        })
        .create();
        
        dialog.show();
        
    }
}