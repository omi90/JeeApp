package com.newsreader.readerlib;

import com.example.jee.R;
import java.text.DateFormat;
import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class SingleItemAdapterNews extends ArrayAdapter<RssItem> {
	private final Context context;
	private final ArrayList<RssItem> values;
	private RssItem ri;
	public SingleItemAdapterNews(Context context, int textViewResourceId, ArrayList<RssItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		values = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		int list_layout = R.layout.news_list_view;
		View rowView = inflater.inflate(list_layout, parent, false);
		TextView titleView = (TextView) rowView.findViewById(R.id.title);
		TextView descView = (TextView) rowView.findViewById(R.id.desc);
		TextView updated = (TextView) rowView.findViewById(R.id.pubDate);
	//	TextView source = (TextView) rowView.findViewById(R.id.source);
		ri = values.get(position);
		
		titleView.setText(ri.getTitle());
		//System.out.println(ri.getDescription()+"---"+ri.getTitle());
		//descView.setText(ri.getDescription()!=null?ri.getDescription().substring(0, 10):"No description found.");
		
		Log.d("Test",ri.getLink().toString());
		
		String cont=ri.getContent();
		if(cont!=null){
		//Log.d("Test",cont);
		int startSpan=cont.indexOf("<span");
		int closeStartSpan=cont.indexOf(">",startSpan+1);
		int endSpan=cont.indexOf("</span",closeStartSpan+1);
		//Log.d("Test",startSpan+" : "+closeStartSpan+" : "+endSpan );
		String actualCont=cont.substring(closeStartSpan+1, endSpan);
		
	
		descView.setText(ri.getContent()!=null?actualCont:"No  description found.");
		
		}
		else
		{
			descView.setText("No  description found.");
		}
		
		updated.setText(ri.getPubDate()!=null?DateFormat.getDateInstance().format(ri.getPubDate()):"");
		//source.setText(ri.getFeed()!=null?ri.getFeed().getTitle().substring(0, ri.getFeed().getTitle().indexOf(" ")):"");
		return rowView;
		//return null;
	}
}