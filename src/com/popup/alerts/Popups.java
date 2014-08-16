package com.popup.alerts;

import com.example.jee.MainActivity;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Popups {

	private static Activity currtActvty=null;
	private static Activity currtActvty1=null;
	public static void showNetworkerror(Activity currentActivity,final boolean exit){
		
		currtActvty=currentActivity;
		AlertDialog.Builder builder= new AlertDialog.Builder(currentActivity);
		builder.setMessage("Unable to reach server, \nPlease check your connectivity.")
				.setTitle("Internet Connection Error")
				.setCancelable(false)
				.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(exit)
						currtActvty.finish();
					}
				});
		
		AlertDialog alertDialog=builder.create();
		alertDialog.show();
		
	}
	
	public static void registrationStatus(final Activity activity,String status,final boolean st){
		
		currtActvty1=activity;
		AlertDialog.Builder builder1=new AlertDialog.Builder(currtActvty1);
		
		
		builder1.setTitle("Registration")
		.setMessage(status)
		.setCancelable(false)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(st){
					Intent intent=new Intent(currtActvty1, MainActivity.class);
					currtActvty1.startActivity(intent);
				}else
				{
					currtActvty1.finish();
				}
				
			}
		});
		AlertDialog alertDialog=builder1.create();
		alertDialog.show();
		
		
	}
	
	
}
