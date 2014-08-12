package com.example.jee;

import com.example.jee.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Syllabus extends Activity implements OnClickListener {
   
	private ImageView physics;
	private ImageView chemistry;
	private ImageView maths;
	private ImageView aptitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syllabus);
		
		physics=(ImageView)findViewById(R.id.physics);
		chemistry=(ImageView)findViewById(R.id.chemistry);
		maths=(ImageView)findViewById(R.id.maths);
		aptitude=(ImageView)findViewById(R.id.apti);
		
		physics.setOnClickListener(this);
		chemistry.setOnClickListener(this);
		maths.setOnClickListener(this);
		aptitude.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.syllabus, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		switch (v.getId()) {
		case R.id.physics:
			System.out.println("cgcj,hkjhkkjhkhkhjkh");
			Intent intent=new Intent(getApplicationContext(), SyllabusPhysics.class);
			startActivity(intent);
			break;
        case R.id.maths:
        	Intent intent1=new Intent(getApplicationContext(), SyllabusMathematics.class);
			startActivity(intent1);
			break;
        case R.id.chemistry:
        	Intent intent2=new Intent(getApplicationContext(), SyllabusChemistry.class);
			startActivity(intent2);
     	break;
        case R.id.apti:
        	Intent intent3=new Intent(getApplicationContext(), SyllabusAptitude.class);
			startActivity(intent3);
	   break;
		

		default:
			break;
		}
	}
}
