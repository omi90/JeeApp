package com.newsreader.readerlib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import com.example.jee.News;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;


public class FeedReader_Background extends AsyncTask{
	private String source;
	private URL feed_url;
	private ArrayList<RssItem> rssItems = null;
	private Context context;
	private News news;
	public FeedReader_Background(String source,URL url,News news,Context c) {
		 System.out.println("in background");
		this.source = source;
		feed_url = url;
		this.news = news;
		context = c;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		//ArrayList<RssItem> rssItems = null;]
	
		if(feed_url!=null){
			System.out.println(feed_url);
			//DBConnection2 dbc = new DBConnection2(context);
			try {
					RssFeed feed = RssReader.read(feed_url,this.context);
					rssItems = feed.getRssItems();
					//System.out.println("Result of update>> "+dbc.updateSource(source, feed.getPubDate(), feed.getLastBuildDate(), feed.getTtl()));
					
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(FileNotFoundException  e){
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		this.news.updateListView(rssItems);
		//DBConnection2 dbc = new DBConnection2(context);
		//dbc.open(true);
		//System.out.println("Result of adding feeds>> "+dbc.addFeeds(source, rssItems));
		//dbc.close();
	}
}
