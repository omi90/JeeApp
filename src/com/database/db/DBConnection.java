package com.database.db;

import java.util.ArrayList;
import java.util.Date;

import com.newsreader.readerlib.RssItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class DBConnection {/*
	//TABLE FOR SOURCES OF FEEDS 
	private static final String KEY_ID = "id";
	public static final String KEY_SOURCE = "SOURCE";
	public static final String KEY_SOURCELINK = "SOURCELINK";
	public static final String KEY_PUBDATE = "PUBDATE";
	public static final String KEY_LASTBUILDDATE = "LASTBUILDDATE";
	public static final String KEY_DESCRIPTION = "DESCRIPTION";
	public static final String KEY_SITELINK = "SITELINK";
	public static final String KEY_LANGUAGE = "LANGUAGE";
	public static final String KEY_TTL = "TTL";
	
	private static final String DB_NAME = "NEWSFEED";
	private static final String DB_TABLE = "FEEDSOURCES";
	private static final int DB_VERSION = 2;
	
	//TABLE FOR CONTAINING FEEDS OF SOURCES
	public static final String KEY_FEEDTITLE = "FEEDTITLE";
	public static final String KEY_FEEDLINK = "FEEDLINK";
	public static final String KEY_FEEDCATEGORY = "FEEDCATEGORY";
	public static final String KEY_FEEDIMAGE = "FEEDIMAGE";
	public static final String KEY_FEEDVIDEO = "FEEDVIDEO";
	public static final String KEY_SOURCESITE = "SOURCESITE";
	public static final String KEY_FEEDPUBDATE = "FEEDPUBDATE";
	public static final String KEY_FEEDDESCRIPTION = "DESCRIPTION";
	public static final String KEY_FEEDGUID = "GUID";
	
	private static final String DB2_TABLE = "FEEDDATA";
	
	private static String[][] sources={{"NDTV","/mnt/sdcard/download/Lsgd.xml"}}; 
	private SliteHelper helper;
	private final Context context;
	private SQLiteDatabase database;
	
	public DBConnection(Context c){
		context = c;
	}
	
	public synchronized DBConnection open(boolean writeMode) throws SQLException{
		helper = new SliteHelper(context);
		if(writeMode)
			database = helper.getWritableDatabase();
		else
			database = helper.getReadableDatabase();
		return this;
	}
	public void close() {
		if(database!=null && database.isOpen())
			database.close();
	}
	public synchronized long addSource(String source,String sourceLink,String desc) throws SQLException{
		ContentValues cv = new ContentValues();
		cv.put(KEY_SOURCE, source);
		cv.put(KEY_SOURCELINK, sourceLink);
		cv.put(KEY_DESCRIPTION, desc);
		return database.insert(DB_TABLE, null,cv);
	}
	public synchronized boolean needUpdate(String source){
		try{
			Cursor c = database.rawQuery("SELECT "+KEY_TTL+","+KEY_PUBDATE+" FROM "+DB_TABLE+
											" where "+KEY_SOURCE+"='"+source, null);
			if (c != null ) {
				if  (c.moveToFirst()) {
					do {
						long ttl = Long.parseLong(c.getString(c.getColumnIndex(KEY_TTL)))*120000;
						long pubDate =  Long.parseLong(c.getString(c.getColumnIndex(KEY_PUBDATE)));
						long curTime = (new Date()).getTime();
						if(curTime < ttl+pubDate)
							return true;
					}while (c.moveToNext());
				}
			}
			c.close();
		}
		catch(SQLException sqex){
			return true;
		}
		return false;
	}
	public synchronized int updateSource(String source,Date pubDate,Date lastbuild,String ttl) throws SQLException{
		ContentValues cv = new ContentValues();
		cv.put(KEY_PUBDATE, pubDate.getTime());
		cv.put(KEY_LASTBUILDDATE, lastbuild.getTime());
		cv.put(KEY_TTL, ttl);
		String[] sources = {source};
		return database.update(DB_TABLE, cv,KEY_SOURCE+"= ?", sources);
	}
	
	public synchronized int deleteSource(String source) throws SQLException{
		String[] sources = {source};
		return database.delete(DB_TABLE, KEY_SOURCE+"= ?", sources);
	}
	public synchronized boolean addFeeds(String source,ArrayList<RssItem> items) throws SQLException{
		boolean success = true;
		try{
			database.beginTransaction();
			String sql = "Insert or Replace into "+DB2_TABLE+" ("+KEY_FEEDTITLE+","+KEY_FEEDCATEGORY+","+KEY_FEEDDESCRIPTION+", "+KEY_FEEDLINK+","+KEY_FEEDPUBDATE+","+KEY_FEEDGUID+","+KEY_SOURCESITE+") values(?,?,?,?,?,?,?)";
            SQLiteStatement insert = database.compileStatement(sql);
            int cnt=0;
            for(int i=0;i<items.size();i++){
                RssItem item = items.get(i);
                
                insert.bindString(1, item.getTitle());
                System.out.println(item.getTitle());
                insert.bindString(2, item.getCategory());
                insert.bindString(3, item.getDescription());
                insert.bindString(4, item.getLink());
                insert.bindString(5, ""+item.getPubDate().getTime());
                insert.bindString(6, ""+item.getGuid());
                insert.bindString(7, source);
                if(insert.executeInsert()!=-1)
                	cnt++;
                //insert.execute();
                
            }
			database.setTransactionSuccessful();
			System.out.println(cnt);
		}
		catch(Exception x){
			success = false;
			System.out.println("Printing stack trace>> ");
			x.printStackTrace();
		}
		finally{
			database.endTransaction();
		}
		return success;
	}
	public synchronized ArrayList<RssItem> getFeeds(String source){
		ArrayList<RssItem> items = new ArrayList<RssItem>();
		Cursor c = database.rawQuery("SELECT * FROM "+DB2_TABLE+
		           " where "+KEY_SOURCESITE+"='"+source+"' ORDER BY "+KEY_FEEDPUBDATE, null);
		Cursor c = database.rawQuery("SELECT * FROM "+DB2_TABLE,null);
		int cnt =0;
		if (c != null ) {
		    if  (c.moveToFirst()) {
		        do {
		        	RssItem item = new RssItem();
		            item.setTitle(c.getString(c.getColumnIndex(KEY_FEEDTITLE)));
		            System.out.println();
		            item.setDescription(c.getString(c.getColumnIndex(KEY_FEEDDESCRIPTION)));
		            item.setCategory(c.getString(c.getColumnIndex(KEY_FEEDCATEGORY)));
		            item.setLink(c.getString(c.getColumnIndex(KEY_FEEDLINK)));
		            item.setPubDate(new Date(Long.parseLong(c.getString(c.getColumnIndex(KEY_FEEDPUBDATE)))));
		            item.setGuid(Long.parseLong(c.getString(c.getColumnIndex(KEY_FEEDGUID))));
		            items.add(item);
		            cnt++;
		        }while (c.moveToNext());
		    }
		}
		c.close();
		System.out.println("Count in getFeeds= "+cnt);
		return items;
	}
	private static class SliteHelper extends SQLiteOpenHelper{
		public SliteHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String create_sql = "CREATE TABLE "+DB_TABLE+" ("+
								KEY_ID + " INTEGER PRIMARY KEY," +
								KEY_SOURCE+" TEXT UNIQUE, "+
								KEY_SOURCELINK+" TEXT, "+
								KEY_DESCRIPTION+" TEXT, "+
								KEY_PUBDATE+" TEXT, "+
								KEY_LANGUAGE+" TEXT, "+
								KEY_TTL+" TEXT, "+
								KEY_LASTBUILDDATE+" TEXT)";
			db.execSQL(create_sql);
			create_sql = "CREATE TABLE "+DB2_TABLE+" ("+
					KEY_ID + " INTEGER PRIMARY KEY," +
					KEY_FEEDTITLE+" TEXT, "+
					KEY_FEEDLINK+" TEXT, "+
					KEY_FEEDPUBDATE+" TEXT, "+
					KEY_FEEDCATEGORY+" TEXT, "+
					KEY_FEEDIMAGE+" TEXT, "+
					KEY_FEEDVIDEO+" TEXT, "+
					KEY_SOURCESITE+" TEXT, "+
					KEY_FEEDGUID+" TEXT, "+
					KEY_FEEDDESCRIPTION+" TEXT)";
			db.execSQL(create_sql);
			for(String[] row: sources){
				ContentValues cv = new ContentValues();
				cv.put(KEY_SOURCE, row[0]);
				cv.put(KEY_SOURCELINK, row[1]);
				db.insert(DB_TABLE, null, cv);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+DB2_TABLE);
			onCreate(db);
		}
	}
*/}
