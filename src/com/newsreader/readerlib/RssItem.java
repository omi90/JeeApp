/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.newsreader.readerlib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RssItem implements Comparable<RssItem>, Parcelable {

	private RssFeed feed;
	private String title;
	private String mainlink;
	private Date updated;
	//private String description;
	private String content;
//	private String category;
	//private long guid;
	
	public RssItem() {
		
	}
	
	public RssItem(Parcel source) {
		
		Bundle data = source.readBundle();
		title = data.getString("title");
		mainlink = data.getString("mainlink");
		updated = (Date) data.getSerializable("updated");
	//	description = data.getString("description");
		content = data.getString("content");
		feed = data.getParcelable("feed");
		
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		Bundle data = new Bundle();
		data.putString("title", title);
		data.putString("mainlink", mainlink);
		data.putSerializable("updated", updated);
		//data.putString("description", description);
		data.putString("content", content);
		data.putParcelable("feed", feed);
		dest.writeBundle(data);
	}
	
	public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>() {
		public RssItem createFromParcel(Parcel data) {
			return new RssItem(data);
		}
		public RssItem[] newArray(int size) {
			return new RssItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
	
	public RssFeed getFeed() {
		return feed;
	}

	public void setFeed(RssFeed feed) {
		this.feed = feed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainlink() {
		return mainlink;
	}

	public void setMainlink(String mainlink) {
		System.out.println(getTitle()+" >>>>>> "+mainlink);
		this.mainlink = mainlink;
	}

	public Date getPubDate() {
		return updated;
	}

	public void setPubDate(Date pubDate) {
		this.updated = pubDate;
	}

	public void setPubDate(String pubDate) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			this.updated = dateFormat.parse(pubDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	@Override
	public int compareTo(RssItem another) {
		if(getPubDate() != null && another.getPubDate() != null) {
			return getPubDate().compareTo(another.getPubDate());
		} else { 
			return 0;
		}
	}
	
}
