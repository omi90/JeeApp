package com.expandablelistview.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jee.R;

public class ExpandableListAdaptor extends BaseExpandableListAdapter {
	
	private ArrayList<String> arrayList;
	private List<String> contentList;
	private Context context;
	
	public ExpandableListAdaptor(Context context,ArrayList<String> arrayList,List<String> contentList){
		this.context= context;
		this.arrayList=arrayList;
		this.contentList=contentList;
		
	}
	

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		
		return this.arrayList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.arrayList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
	return	this.contentList.get(groupPosition);
		
		
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final String title=(String)getGroup(groupPosition);
		
		System.out.println(title);
		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.syllabus_main_list_item, parent,false);
			
		}
		TextView textView=(TextView)convertView.findViewById(R.id.dataMain);
		textView.setText(title);
		
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final String childText=(String)getChild(groupPosition, childPosition);
		
		System.out.println(childPosition);
		
		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.syllabus_child_list_item, parent, false);
			
		}
		TextView textView=(TextView)convertView.findViewById(R.id.data);
		textView.setText(Html.fromHtml(childText));
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
