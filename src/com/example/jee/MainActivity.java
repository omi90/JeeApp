package com.example.jee;

import com.example.jee.R;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity implements AnimationListener {

	private Animation am,am2;
	private View syllabus;
	private View books;
	private View news;
	private View facebook;
	private View gmail;
	private View query;
	private View papers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		syllabus = (View) findViewById(R.id.syllabus);
		books = (View) findViewById(R.id.books);
		facebook =(View) findViewById(R.id.facebook);
		gmail = (View)findViewById(R.id.gmail);
		query = (View)findViewById(R.id.query);
		papers =(View) findViewById(R.id.papers);
		news=(View)findViewById(R.id.news);
		am = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down);
		am2=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
	    // am2=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.)

		am2.setAnimationListener(this);
		

		
		syllabus.startAnimation(am);
		books.startAnimation(am);
		facebook.startAnimation(am);
		gmail.startAnimation(am);
		query.startAnimation(am);
		syllabus.startAnimation(am);
		papers.startAnimation(am);
		news.startAnimation(am);
		
		syllabus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),Syllabus.class);
				startActivity(in);

			}
		});
		
		gmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "amit_jmit@yaho.com"));
			//	intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
			//	intent.putExtra(Intent.EXTRA_TEXT, "your_text");
				startActivity(intent);
			}
		});
		
		books.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent in = new Intent(getApplicationContext(),Books.class);
				startActivity(in);
				
			}
		});
		
		news.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),News.class);
				startActivity(in);
			}
		});
		

		query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent intent=new Intent(getApplicationContext(), Queries.class);
				startActivity(intent);
			}
		});
		
		facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

               Intent facebookIntent = getOpenFacebookIntent(MainActivity.this);
              startActivity(facebookIntent);
			}
		});
		
		papers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent=new Intent(getApplicationContext(), PracticePapers.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		  MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.activity_action, menu);
		    return super.onCreateOptionsMenu(menu);
	
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}
	

public static Intent getOpenFacebookIntent(Context context) {
 
    try {
    	System.out.println("fb");
        context.getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
        return new Intent(Intent.ACTION_VIEW,Uri.parse("fb://profile/421873334574035")); //Trys to make intent with FB's URI
    } catch (Exception e) {
        return new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/jeemainnews")); //catches and opens a url to the desired page
    }
}


}
