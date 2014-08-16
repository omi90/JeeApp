package com.example.jee;

import java.util.regex.Pattern;

import com.mail.api.GMailSender;
import com.popup.alerts.Popups;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.Toast;

public class Queries extends Activity {

	private ActionBar actionBar;
	
	private EditText title;
	private EditText desc;
	private Button send;
	final Pattern pattern=android.util.Patterns.EMAIL_ADDRESS;
	private String email;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queries);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		title=(EditText)findViewById(R.id.ques);
		desc=(EditText)findViewById(R.id.descn);
		send=(Button)findViewById(R.id.query_send);
		
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				email=preferences.getString("email","null");
				
				handler=new Handler();
				
				if(title.getText().length()==0)
				{
					Toast toast1=Toast.makeText(getApplicationContext(), "Please enter title !!",Toast.LENGTH_LONG);
					toast1.show();
					title.requestFocus();
				}else if(desc.getText().length()==0){
					Toast toast2=Toast.makeText(getApplicationContext(), "Please enter description !!",Toast.LENGTH_LONG);
					toast2.show();
					title.requestFocus();
				}
				else{
					ConnectivityManager manager= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
					if (manager.getActiveNetworkInfo() == null) {
							Popups.showNetworkerror(Queries.this,false);
						} else {
							 
							
						
							Thread thread = new Thread(new Runnable() {
								
								@Override
								public void run() {
									
									try{
									// TODO Auto-generated method stub
								 GMailSender sender = new GMailSender("ultimo.developers.mailserver@gmail.com", "ultimoDev90");
					                    String msg= "Email ID : "+ email+"<br>Title/Question :"+ title.getText()+"<br>Description:"+desc.getText()+
					                    		"<br><br><br>Thanks And Regards<br>Ultimo Developers<br>www.ultimodevelopers.com";
					                  
									 handler.post(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											
											title.setEnabled(false);
											desc.setEnabled(false);
											send.setEnabled(false);
											send.setText("Sending your queries/suggestion..");
										}
									});
									  
									 sender.sendMail("Important!!! --JEE MAIN (Android APP) -- Query/Suggestion Request",   
					                            msg,   
					                            "ultimo.developers.mailserver@gmail.com",   
					                           "amit_jmit@yahoo.com");  
									 handler.post(new Runnable() {
											
											@Override
											public void run() {
												Popups.registrationStatus(Queries.this, "Succesfully Posted!!\n We will shortly back to you \n Be with your mail box ..", true);
											}
									 });
									
								}
									catch(Exception e){
										e.printStackTrace();
										
										handler.post(new Runnable() {
											
											@Override
											public void run() {
												// TODO Auto-generated method stub
												 Popups.registrationStatus(Queries.this, "There is some error while posting.\nPlease try again...", false);
												
											}
										});
										
									}
								}
								
								
							});
						 thread.start();
							
						}
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.queries, menu);
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
