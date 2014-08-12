package com.example.jee;

import java.util.ArrayList;
import java.util.HashMap;

import com.expandablelistview.lib.ExpandableListAdaptor;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class SyllabusPhysics extends Activity {

	ExpandableListAdaptor listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, String> listDataChild;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syllabus_physics);
		System.out.println("in syllabus");
		expListView=(ExpandableListView)findViewById(R.id.expand);
		
		listDataHeader=new ArrayList<String>();
		listDataHeader.add("UNIT 1: Physics and Measurement");
		listDataHeader.add("UNIT 2: Kinematics");
		listDataHeader.add("UNIT 3: Laws of Motion");
		listDataHeader.add("UNIT 4: Work, Energy and Power");
		listDataHeader.add("UNIT 5: Rotational Motion");
		listDataHeader.add("UNIT 6: Gravitation");
		listDataHeader.add("UNIT 7: Properties of Solids and Liquids");
		listDataHeader.add("UNIT 8: Thermodynamics");
		listDataHeader.add("UNIT 9: Kinetic Theory of Gases");
		listDataHeader.add("UNIT 10: Oscillations and Waves");
		listDataHeader.add("UNIT 11: Electrostatics");
		listDataHeader.add("UNIT 12: Current Electricity");
		listDataHeader.add("UNIT 13: Magnetic Effects of Current and Magnetism");
		listDataHeader.add("UNIT 14: Electromagnetic Induction and Alternating Currents");
		listDataHeader.add("UNIT 15: Electromagnetic Waves");
		
		// Adding child data
        ArrayList<String> unit1 = new ArrayList<String>();
        ArrayList<String> top250 = new ArrayList<String>();
        unit1.add("Physics, technology and society, S I units, Fundamental and derived units. Least count, accuracy and precision of measuring instruments, Errors in measurement, Significant figures. Dimensions of Physical quantities, dimensional analysis and its applications. ");
        unit1.add("Frame of reference. Motion in a straight line: Position-time graph, speed and velocity. Uniform and non-uniform motion, average speed and instantaneous velocity Uniformly accelerated motion, velocity-time, position- time graphs, relations for uniformly accelerated motion. Scalars and Vectors, Vector addition and Subtraction, Zero Vector, Scalar and Vector products, Unit Vector, Resolution of a Vector. Relative Velocity, Motion in a plane, Projectile Motion, Uniform Circular Motion.");
        unit1.add("Force and Inertia, Newton’s First Law of motion; Momentum, Newton’s Second Law of motion; Impulse; Newton’s Third Law of motion. Law of conservation of linear momentum and its applications, Equilibrium of concurrent forces.\nStatic and Kinetic friction, laws of friction, rolling friction.\nDynamics of uniform circular motion: Centripetal force and its applications.");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
        
		/*
        listDataChild= new HashMap<String, String>();
        
        listDataChild.put(listDataHeader.get(0), unit1.get(0)); // Header, Child data
       listDataChild.put(listDataHeader.get(1), unit1.get(1));
       listDataChild.put(listDataHeader.get(2), unit1.get(2));
		
		
       System.out.println(listDataHeader.get(0));
       System.out.println(listDataHeader.get(1));
       */
       
		 listAdapter = new ExpandableListAdaptor(this, listDataHeader, unit1);
		 
		 	
		expListView.setAdapter(listAdapter);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.syllabus_data, menu);
		
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
