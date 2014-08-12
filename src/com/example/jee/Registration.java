package com.example.jee;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mail.api.GMailSender;
import com.popup.alerts.Popups;

public class Registration extends Activity {
	
	private TextView email;
	private TextView name;
	private TextView number;
	private TextView location;
	private Button register;
	
	private Handler handler;
	//Context context=getApplicationContext();
	//SharedPreferences preferences=context.getp

	String possibleEmail="";
	
	final Pattern pattern=android.util.Patterns.EMAIL_ADDRESS;
	//private Pattern pattern2= android.util.Patterns.PHONE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		email =(TextView)findViewById(R.id.email);
		name =(TextView)findViewById(R.id.name);
		number =(TextView)findViewById(R.id.number);
		location =(TextView)findViewById(R.id.location);
		register=(Button)findViewById(R.id.register);
		
		handler=new Handler();
		
		
	
		
		Account[] accounts= AccountManager.get(getApplicationContext()).getAccountsByType("com.google");
		
		for (Account account : accounts) {
			if (pattern.matcher(account.name).matches()) {
			possibleEmail = account.name;
		
		      	}
			}
		email.setText(possibleEmail);
		
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean test=true;
							
				if(email.getText().length()==0){
			
					email.setHint("Empty Email");
					email.setHintTextColor(Color.RED);
					test=false;
				}
				else if (!pattern.matcher(email.getText()).matches()) {
				
					email.setTextColor(Color.RED);
					test=false;
				}
				else{
					
					email.setTextColor(Color.BLACK);
				}
				
				if (name.getText().length()==0) {
					name.setHint("Empty Full Name");
					name.setHintTextColor(Color.RED);
					test=false;
				}else
				{
					name.setTextColor(Color.BLACK);
				}
				
				if (number.getText().length()==0) {
					number.setHint("Empty Number");
					number.setHintTextColor(Color.RED);
					test=false;
				}else
				{ 
					 if(number.getText().length()!=10){
						 
						 number.setText("");
						 number.setHint("Invalid Number");
						 number.setHintTextColor(Color.RED);
						 test=false;
					 }else
					 {
						 number.setTextColor(Color.BLACK);
					}
					
				}
				if (location.getText().length()==0) {
					location.setHint("Empty Location");
					location.setHintTextColor(Color.RED);
					test=false;
				}else
				{
					location.setTextColor(Color.BLACK);
				}
				
				if(test){
					
					ConnectivityManager manager= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
					if (manager.getActiveNetworkInfo() == null) {
							Popups.showNetworkerror(Registration.this,false);
						} else {
					
					
					Thread t = new Thread(new Runnable() {
						public void run(){
					 try {   
		                    GMailSender sender = new GMailSender("ultimo.developers.mailserver@gmail.com", "ultimoDev90");
		                    String msg= "Email ID : "+ email.getText()+"<br>Name :"+ name.getText()+"<br>Phone Number:"+number.getText()+
		                    		"<br>Location:"+location.getText()+"<br><br><br>Thanks And Regards<br>Ultimo Developers<br>www.ultimodevelopers.com";
		                    Log.d("Mail","Sending email with text > "+msg);
		                    
		                    handler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									   email.setEnabled(false);
									   email.setTextColor(Color.GRAY);
					                    name.setEnabled(false);
					                    name.setTextColor(Color.GRAY);
					                    number.setEnabled(false);
					                    number.setTextColor(Color.GRAY);
					                    location.setEnabled(false);
					                    location.setTextColor(Color.GRAY);
					                    register.setEnabled(false);
					                    register.setText("In progress.....");
								}
							});
		                    
		                   
		                    
		                    
		                    sender.sendMail("JEE MAIN -- Android APP -- New Registration Information",   
		                            msg,   
		                            "ultimo.developers.mailserver@gmail.com",   
		                           "amit_jmit@yahoo.com");  
		                    
		                    handler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
									Editor  editor=preferences.edit();
									editor.putString("email", email.getText().toString());
									editor.putLong("phone", Long.parseLong(number.getText().toString()));
									editor.putString("name", name.getText().toString());
									editor.putString("location", location.getText().toString());
									editor.putBoolean("status",true);
									editor.apply();
									
									Popups.registrationStatus(Registration.this, "Congratulation !!\n Registration Succesfully Done..", true);
								}
							});
		                      
		                    
		                    
		                } catch (Exception e) { 
		                	System.out.println("exception");
		                    Log.d("SendMail", e.getMessage(), e); 
		                    handler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									 Popups.registrationStatus(Registration.this, "There is some error while registration.\nPlease try again...", false);
									 email.setEnabled(true);
					                    name.setEnabled(true);
					                    number.setEnabled(true);
					                    location.setEnabled(true);
					                    register.setEnabled(true);
					                    register.setText("Register...");
					                    name.setTextColor(Color.BLACK);
					                    number.setTextColor(Color.BLACK);
					                    location.setTextColor(Color.BLACK);
					                    email.setTextColor(Color.BLACK);
								}
							});
		                   
		                 
		                } 
						}
					});
					t.start();
					
				}
				}
				
			}
		});
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
