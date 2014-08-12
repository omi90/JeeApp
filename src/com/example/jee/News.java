package com.example.jee;

import com.example.jee.R;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newsreader.readerlib.FeedReader_Background;
import com.newsreader.readerlib.RssItem;
import com.newsreader.readerlib.SingleItemAdapterNews;
import com.popup.alerts.Popups;

public class News extends Activity {

	private FeedReader_Background bg;
	private ListView listView;
	private String[] sourceData={"JEE1","http://jee-main.co.in/feeds/posts/default?max-results=5"};
//	private URL url="https://news.google.co.in/news/feeds?pz=1&cf=all&ned=in&hl=en&topic=tc&output=rss";
	private Handler handler;
	private ArrayList<RssItem> arrayList;
	private Thread thread;
	private Boolean shouldrun=true;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		actionBar=getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		arrayList = new ArrayList<RssItem>();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() == null
			) {
			Popups.showNetworkerror(this,true);
		} else {
			
			handler=new Handler();
			listView=(ListView)findViewById(R.id.list1);
			try {
				bg=new FeedReader_Background(sourceData[0],new URL(sourceData[1]),News.this, getApplicationContext());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bg.execute((Object[])null);
			/*thread =new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(shouldrun){
						
						
						if(arrayList!=null && arrayList.size()>listView.getCount()){
							handler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									if(arrayList.size()>0){
										listView.setAdapter(new SingleItemAdapterNews(getApplicationContext(), R.layout.news_list_view, arrayList));
										TextView emptylist = ((TextView)findViewById(R.id.emptylist));
										emptylist.setText("");
										ProgressBar pB=(ProgressBar)findViewById(R.id.progressBar);
										pB.invalidate();
									
									}
								}
							});
						}
						try {
							Thread.sleep(60000);
							arrayList = null;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
				
			thread.start();*/
			
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(bg!=null){
		bg.cancel(true);}
		bg = null;
		if(thread!=null){
			shouldrun = false;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.news, menu);
		 return super.onCreateOptionsMenu(menu);
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

	public void updateListView(final ArrayList<RssItem> rssItems) {
		// TODO Auto-generated method stub
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(rssItems.size()>0){
					listView.setAdapter(new SingleItemAdapterNews(getApplicationContext(), R.layout.news_list_view, rssItems));
					TextView emptylist = ((TextView)findViewById(R.id.emptylist));
					emptylist.setText("");
					ProgressBar pB=(ProgressBar)findViewById(R.id.progressBar);
					pB.setVisibility(View.INVISIBLE);
				   pB.invalidate();
				
				}
			}
		});
	}
}
